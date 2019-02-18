package com.vincent.playlisttransferutility.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.ui.base.BaseFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.utils.BooleanUtils
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button
    private lateinit var startTransferButton: Button

    private lateinit var googleLoginDialog: GoogleLoginDialogFragment

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //googleLoginDialog = GoogleLoginDialogFragment()
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

    override fun subscribeToViewModelEvents() {
        compositeDisposable.addAll(
                viewModel.getToastEvents().subscribe(this::onToastMessageReceived),
                viewModel.getSpotifyLoginRequestEvents().subscribe(this::onSpotifyLoginRequestReceived),
                viewModel.getGoogleLoginRequestEvents().subscribe { onGoogleLoginRequestReceived() },
                viewModel.getViewStateEvents().subscribe(this::onViewStateUpdateReceived)
        )
    }

    private fun onToastMessageReceived(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun onSpotifyLoginRequestReceived(request: AuthenticationRequest) {
        showSpotifyLoginDialog(request)
    }

    private fun onGoogleLoginRequestReceived() {
        googleLoginDialog.show(fragmentManager!!, GoogleLoginDialogFragment.TAG)
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

    private fun showSpotifyLoginDialog(request: AuthenticationRequest) {
        AuthenticationClient.openLoginActivity(activity!!, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }
}
