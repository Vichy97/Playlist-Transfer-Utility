package com.vincent.playlisttransferutility.ui.main

import android.util.Log
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val spotifyRepository: SpotifyRepository) {

    init {
        Log.d("1", "")
    }

    fun saveSpotifyAuthToken(authenticationResponse: AuthenticationResponse): Completable {
        val authToken: AuthToken = AuthToken.fromSpotifyAuthenticationResponse(authenticationResponse)
        return spotifyRepository.setSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return spotifyRepository.getSpotifyAuthToken()
    }
}