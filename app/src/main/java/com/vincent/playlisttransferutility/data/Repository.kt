package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor() {

    private var spotifyAuthToken: AuthToken? = null

    private val repositoryComponent: RepositoryComponent
    private val authTokenDataSource: AuthTokenDataSource
    private val spotifyApi: SpotifyApi

    init {
        repositoryComponent = DaggerRepositoryComponent.builder().build()
        spotifyApi = repositoryComponent.getSpotifyApi()
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