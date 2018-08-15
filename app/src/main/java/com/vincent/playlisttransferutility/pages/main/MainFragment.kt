package com.vincent.playlisttransferutility.pages.main

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.FragmentMainBinding
import com.vincent.playlisttransferutility.utils.BooleanUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button

    private lateinit var startTransferButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spotifyButton = btn_spotify_button
        googlePlayMusicButton = btn_google_play_music_button
        appleMusicButton = btn_apple_music_button
        startTransferButton = btn_start_transfer_button
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.onActivityResult(requestCode, resultCode, data)
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
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
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
        AuthenticationClient.openLoginActivity(activity!!, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }
}
