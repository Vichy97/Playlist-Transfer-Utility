package com.vincent.playlisttransferutility.pages.main

import com.github.felixgail.gplaymusic.util.TokenProvider
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Completable
import io.reactivex.Single

class MainModel {

    private val repository: Repository

    init {
        val mainComponent: MainComponent = DaggerMainComponent.builder().build()
        repository = mainComponent.getRepository()
    }

    fun saveSpotifyAuthToken(authenticationResponse: AuthenticationResponse) {
        val authToken: AuthToken = AuthToken.fromSpotifyAuthenticationResponse(authenticationResponse)
        repository.setSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        //TODO: check time to live against current time and return null if expired
        return repository.getSpotifyAuthToken()
    }

    fun saveGooglePlayAuthToken(authToken: AuthToken) {
        repository.setGooglePlayAuthToken(authToken)
    }

    fun getGooglePlayAuthToken(): Single<AuthToken> {
        return repository.getGooglePlayAuthToken()
    }

    fun loginToGooglePlay(username: String, password: String): Completable {
        return Completable.create {
            val authToken: AuthToken = AuthToken.fromGooglePlayAuthToken(TokenProvider
                    .provideToken(username, password, "EMEI"))
            repository.setGooglePlayAuthToken(authToken)
            it.onComplete()
        }
    }

}