package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Observable
import io.reactivex.Single

class Repository {

    private val repositoryComponent: RepositoryComponent
    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private val googlePlayMusicService: GPlayMusic
    private val spotifyHeaderInterceptor: HeaderInterceptor
    private var spotifyAuthToken: AuthToken? = null
    private var googlePlayAuthToken: AuthToken? = null

    init {
        repositoryComponent = DaggerRepositoryComponent.builder().build()
        dataSource = repositoryComponent.getAuthTokenDataSource()
        spotifyApi = repositoryComponent.getSpotifyApi()
        spotifyHeaderInterceptor = repositoryComponent.getSpotifyHeaderInterceptor()
        googlePlayMusicService = repositoryComponent.getGooglePlayMusicService()

        spotifyAuthToken = dataSource.getSpotifyAuthToken()
        if (spotifyAuthToken != null) {
            spotifyHeaderInterceptor.setAccessToken(spotifyAuthToken!!.accessToken)
        }
        googlePlayAuthToken = dataSource.getGooglePlayAuthToken()
    }

    fun setSpotifyAuthToken(authToken: AuthToken) {
        spotifyAuthToken = authToken
        spotifyHeaderInterceptor.setAccessToken(authToken.accessToken)
        dataSource.saveSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return Single.just(spotifyAuthToken)
    }

    fun setGooglePlayAuthToken(authToken: AuthToken) {
        googlePlayAuthToken = authToken
        googlePlayMusicService.changeToken(svarzee.gps.gpsoauth.AuthToken(authToken.accessToken))
        dataSource.saveGooglePlayAuthToken(authToken)
    }

    fun getGooglePlayAuthToken(): Single<AuthToken> {
        return Single.just(spotifyAuthToken)
    }

    fun getSpotifyPlaylists(): Observable<List<SpotifyPlaylist>> {
        if (spotifyAuthToken == null) {
            return Observable.empty()
        }

        //TODO: cache these... also make use of pagination in the future
        return spotifyApi.getAllPlaylists(null, null).flatMapObservable {
            Observable.just(it.items)
        }
    }
}