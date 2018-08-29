package com.vincent.playlisttransferutility.ui.main

import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Completable
import io.reactivex.Single

class MainModel(private val repository: Repository) {

    fun saveSpotifyAuthToken(authenticationResponse: AuthenticationResponse): Completable {
        val authToken: AuthToken = AuthToken.fromSpotifyAuthenticationResponse(authenticationResponse)
        return repository.setSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return repository.getSpotifyAuthToken()
    }

    fun getGooglePlayAuthToken(): Single<AuthToken> {
        return repository.getGooglePlayAuthToken()
    }
}