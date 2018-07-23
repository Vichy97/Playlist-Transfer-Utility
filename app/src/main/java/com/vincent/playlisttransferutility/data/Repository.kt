package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.data.models.spotify.Playlist
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Observable

class Repository {

    private val repositoryComponent: RepositoryComponent
    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private val spotifyHeaderInterceptor: HeaderInterceptor

    private var spotifyAuthToken: AuthToken? = null

    init {
        repositoryComponent = DaggerRepositoryComponent.builder().build()
        dataSource = repositoryComponent.getAuthTokenDataSource()
        spotifyApi = repositoryComponent.getSpotifyApi()
        spotifyHeaderInterceptor = repositoryComponent.getSpotifyHeaderInterceptor()

        spotifyAuthToken = dataSource.getSpotifyAuthToken()
        if (spotifyAuthToken != null) {
            spotifyHeaderInterceptor.setAccessToken(spotifyAuthToken!!.accessToken)
        }
    }

    fun getSpotifyAuthToken(): Observable<AuthToken> {
        return Observable.just(spotifyAuthToken)
    }

    fun setSpotifyAuthToken(authToken: AuthToken) {
        spotifyAuthToken = authToken
        spotifyHeaderInterceptor.setAccessToken(authToken.accessToken)
        dataSource.saveSpotifyAuthToken(authToken)
    }

    fun getSpotifyPlaylists(): Observable<List<Playlist>> {
        //TODO: possibly cache these...
        if (spotifyAuthToken == null) {
            return Observable.empty()
        }

        return spotifyApi.getAllPlaylists().map {
            it.items
        }
    }
}