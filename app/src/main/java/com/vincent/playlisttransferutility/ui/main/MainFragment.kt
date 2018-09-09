package com.vincent.playlisttransferutility.ui.main

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.FragmentMainBinding
import com.vincent.playlisttransferutility.ui.base.BaseFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.utils.BooleanUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button
    private lateinit var startTransferButton: Button

    private lateinit var googleLoginDialog: GoogleLoginDialogFragment

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        googleLoginDialog = GoogleLoginDialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel

        return binding.root
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
                viewModel.getNavigationEvents().subscribe(this::onNavigationEventReceived),
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
        googleLoginDialog.show(fragmentManager, GoogleLoginDialogFragment.TAG)
    }

    private fun onNavigationEventReceived(actionId: Int) {
        navController.navigate(actionId)
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
