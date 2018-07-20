package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import io.reactivex.Observable

class Repository() {

    private var spotifyAuthToken: AuthToken? = null
    private var authTokenDataSource: AuthTokenDataSource;

    init {
        authTokenDataSource = PreferencesAuthTokenDataSource()
        spotifyAuthToken = authTokenDataSource.getSpotifyAuthToken()
    }

    fun getSpotifyAuthToken(): Observable<AuthToken> {
        return Observable.just(authTokenDataSource.getSpotifyAuthToken());
    }

    fun saveSpotifyAuthToken(authToken: AuthToken) {
        authTokenDataSource.saveSpotifyAuthToken(authToken)
    }
}