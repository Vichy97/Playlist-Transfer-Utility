package com.vincent.playlisttransferutility.pages.main

import android.content.Intent
import io.reactivex.Observable
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.BuildConfig
import com.vincent.playlisttransferutility.resources.ResourceProvider
import io.reactivex.subjects.PublishSubject

class MainViewModel {

    companion object {
        const val SPOTIFY_LOGIN_REQUEST_CODE: Int = 1337
        const val SPOTIFY_REDIRECT_URI: String = "playlistutil://main"
    }

    private val mainComponent: MainComponent
    private val mainModel: MainModel
    private val resourceProvider: ResourceProvider

    private val toastMessage: PublishSubject<String> = PublishSubject.create()
    private val spotifyLogin: PublishSubject<AuthenticationRequest> = PublishSubject.create()

    init {
        mainComponent = DaggerMainComponent.builder().build()

        mainModel = mainComponent.getMainModel()
        resourceProvider = mainComponent.getResourceProvider()
    }

    fun getToastMessage(): Observable<String> {
        return toastMessage
    }

    fun getSpotifyLogin(): Observable<AuthenticationRequest> {
        return spotifyLogin
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
                //TODO: create access token and save it
            }

            AuthenticationResponse.Type.ERROR -> {
                //TODO: toast or something
            }
            else -> {
                //TODO: toast or something
            }
        }
    }

    fun onSpotifyClicked() {
        spotifyLogin.onNext(getSpotifyAuthenticationRequest())
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
                        AuthenticationResponse.Type.TOKEN, SPOTIFY_REDIRECT_URI)
        //TODO: stop hardcoding these values
        val scopes: Array<String> = arrayOf("playlist-modify-private", "playlist-modify-public")
        builder.setScopes(scopes).setShowDialog(true)

        return builder.build()
    }
}