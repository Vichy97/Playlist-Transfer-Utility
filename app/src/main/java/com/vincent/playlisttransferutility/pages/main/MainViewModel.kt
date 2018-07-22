package com.vincent.playlisttransferutility.pages.main

import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.BuildConfig
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel() {

    companion object {
        const val SPOTIFY_LOGIN_REQUEST_CODE: Int = 1337
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mainModel: MainModel
    private val resourceProvider: ResourceProvider

    private val toastMessage: PublishSubject<String> = PublishSubject.create()
    private val spotifyLoginRequest: PublishSubject<AuthenticationRequest> = PublishSubject.create()
    private val viewStateSubject: BehaviorSubject<MainViewState> = BehaviorSubject.create()

    private lateinit var viewState: MainViewState

    init {
        val mainComponent: MainComponent = DaggerMainComponent.builder().build()
        mainModel = mainComponent.getMainModel()
        resourceProvider = mainComponent.getResourceProvider()

        initViewState()
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    private fun initViewState() {
        viewState = MainViewState()

       //TODO: get view state from model and pass to view
    }

    fun getToastMessage(): Observable<String> {
        return toastMessage
    }

    fun getSpotifyLoginRequest(): Observable<AuthenticationRequest> {
        return spotifyLoginRequest
    }

    fun getViewState(): Observable<MainViewState> {
        return viewStateSubject
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPOTIFY_LOGIN_REQUEST_CODE) {
            onSpotifyLogin(data, resultCode)
        }
    }

    private fun onSpotifyLogin(data: Intent?, resultCode: Int) {
        val response = AuthenticationClient.getResponse(resultCode, data)
        when (response.type) {
            AuthenticationResponse.Type.TOKEN -> {
                onSpotifyTokenReceived(response)
            }
            else -> {
                toastMessage.onNext(resourceProvider.getString(R.string.login_error))
            }
        }
    }

    private fun onSpotifyTokenReceived(response: AuthenticationResponse) {
        val authToken: AuthToken = AuthToken.fromAuthenticationResponse(response)
        mainModel.saveSpotifyAuthToken(authToken)
        viewState.spotifyLogin = true
        viewStateSubject.onNext(viewState)
    }

    fun onSpotifyClicked() {
        spotifyLoginRequest.onNext(getSpotifyAuthenticationRequest())
    }

    fun onAppleMusicClicked() {
        toastMessage.onNext("Apple Music Clicked")
    }

    fun onGooglePlayMusicClicked() {
        toastMessage.onNext("Google Play Music Clicked")
    }

    fun onStartTransferClicked() {
        toastMessage.onNext("Start Transfer Clicked")
    }

    private fun getSpotifyAuthenticationRequest(): AuthenticationRequest {
        val builder: AuthenticationRequest.Builder =
                AuthenticationRequest.Builder(BuildConfig.SPOTIFY_CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN, getSpotifyRedirectUri())
        //TODO: stop hardcoding these values
        val scopes: Array<String> = arrayOf("playlist-modify-private", "playlist-modify-public")
        builder.setScopes(scopes).setShowDialog(true)

        return builder.build()
    }

    private fun getSpotifyRedirectUri(): String {
        val scheme: String = resourceProvider.getString(R.string.uri_scheme)
        val host: String = resourceProvider.getString(R.string.uri_host_main)
        return "$scheme://$host"
    }
}