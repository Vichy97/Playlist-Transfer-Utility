package com.vincent.playlisttransferutility.pages.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.ActivityMainBinding
import com.vincent.playlisttransferutility.pages.playlistselection.PlaylistSelectionActivity
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mainViewModel: MainViewModel

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupDataBinding()

        spotifyButton = btn_spotify_button
        googlePlayMusicButton = btn_google_play_music_button
        appleMusicButton = btn_apple_music_button
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mainViewModel.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupDataBinding() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()

        subscribeToViewModelEvents()
    }

    private fun subscribeToViewModelEvents() {
        compositeDisposable.addAll(
                mainViewModel.getToastMessageEvents().subscribe(this::onToastMessageReceived),
                mainViewModel.getSpotifyLoginRequestEvents().subscribe(this::onSpotifyLoginRequestReceived),
                mainViewModel.getViewStateEvents().subscribe(this::onViewStateUpdateReceived),
                mainViewModel.getNavigationEvents().subscribe{navigateToPlaylistSelection()}
        )
    }

    private fun onToastMessageReceived(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun onSpotifyLoginRequestReceived(request: AuthenticationRequest) {
        loginToSpotify(request)
    }

    private fun onViewStateUpdateReceived(viewState: MainViewState) {
        //TODO: disable buttons if service is already logged in. Only enable
        //transfer button if at least 2 services are logged in

        //spotifyButton.isEnabled = !viewState.spotifyLogin
        //googlePlayMusicButton.isEnabled = !viewState.googlePlayMusicLogin
        //appleMusicButton.isEnabled = !viewState.appleMusicLogin
    }

    private fun loginToSpotify(request: AuthenticationRequest) {
        AuthenticationClient.openLoginActivity(this, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }

    private fun navigateToPlaylistSelection() {
        val intent: Intent = Intent(this, PlaylistSelectionActivity::class.java)
        startActivity(intent)
    }
}
