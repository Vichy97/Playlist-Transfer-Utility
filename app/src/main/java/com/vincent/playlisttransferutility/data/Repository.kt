package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Observable

class Repository {

    private val repositoryComponent: RepositoryComponent
    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi

    private var spotifyAuthToken: AuthToken? = null

    init {
        repositoryComponent = DaggerRepositoryComponent.builder().build()
        spotifyApi = repositoryComponent.getSpotifyApi()
        dataSource = repositoryComponent.getAuthTokenDataSource()

        spotifyAuthToken = dataSource.getSpotifyAuthToken()
    }

    fun getSpotifyAuthToken(): Observable<AuthToken> {
        return Observable.just(spotifyAuthToken)
    }

    fun saveSpotifyAuthToken(authToken: AuthToken) {
        dataSource.saveSpotifyAuthToken(authToken)
    }
}