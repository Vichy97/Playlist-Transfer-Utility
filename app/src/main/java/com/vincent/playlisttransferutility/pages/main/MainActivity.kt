package com.vincent.playlisttransferutility.pages.main

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.ActivityMainBinding
import com.vincent.playlisttransferutility.utils.BooleanUtils
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button

    private lateinit var startTransferButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupDataBinding()

        spotifyButton = btn_spotify_button
        googlePlayMusicButton = btn_google_play_music_button
        appleMusicButton = btn_apple_music_button
        startTransferButton = btn_start_transfer_button
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupDataBinding() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
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
                viewModel.getToastMessageEvents().subscribe(this::onToastMessageReceived),
                viewModel.getSpotifyLoginRequestEvents().subscribe(this::onSpotifyLoginRequestReceived),
                viewModel.getViewStateEvents().subscribe(this::onViewStateUpdateReceived)
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

        spotifyButton.isEnabled = !viewState.spotifyLogin
        googlePlayMusicButton.isEnabled = !viewState.googlePlayMusicLogin
        appleMusicButton.isEnabled = !viewState.appleMusicLogin

        startTransferButton.isEnabled = BooleanUtils.atLeastTwo(viewState.spotifyLogin,
                viewState.googlePlayMusicLogin,
                viewState.appleMusicLogin)
    }

    private fun loginToSpotify(request: AuthenticationRequest) {
        AuthenticationClient.openLoginActivity(this, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }
}
