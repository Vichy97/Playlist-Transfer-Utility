package com.vincent.playlisttransferutility.ui.main

import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Completable
import io.reactivex.Single

class MainModel(private val spotifyRepository: SpotifyRepository) {

    fun saveSpotifyAuthToken(authenticationResponse: AuthenticationResponse): Completable {
        val authToken: AuthToken = AuthToken.fromSpotifyAuthenticationResponse(authenticationResponse)
        return spotifyRepository.setSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return spotifyRepository.getSpotifyAuthToken()
    }
}