package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.github.felixgail.gplaymusic.model.Playlist
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Repository {

    private val repositoryComponent: RepositoryComponent
    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private var googlePlayMusicService: GPlayMusic? = null
    private val spotifyHeaderInterceptor: HeaderInterceptor
    private var spotifyAuthToken: AuthToken? = null
    private var googlePlayAuthToken: AuthToken? = null

    init {
        repositoryComponent = DaggerRepositoryComponent.builder().build()
        dataSource = repositoryComponent.getAuthTokenDataSource()
        spotifyApi = repositoryComponent.getSpotifyApi()
        spotifyHeaderInterceptor = repositoryComponent.getSpotifyHeaderInterceptor()

        spotifyAuthToken = dataSource.getSpotifyAuthToken()
        if (spotifyAuthToken != null) {
            spotifyHeaderInterceptor.setAccessToken(spotifyAuthToken!!.accessToken)
        }
        googlePlayAuthToken = dataSource.getGooglePlayAuthToken()
        if (googlePlayAuthToken != null) {
            initGooglePlayService(googlePlayAuthToken!!)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    private fun initGooglePlayService(googlePlayAuthToken: AuthToken): Completable {
        return Completable.create {
            googlePlayMusicService = GPlayMusic.Builder()
                    .setAuthToken(svarzee.gps.gpsoauth.AuthToken(googlePlayAuthToken.accessToken))
                    .build()

            it.onComplete()
        }
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
        if (googlePlayMusicService != null) {
            googlePlayMusicService!!.changeToken(svarzee.gps.gpsoauth.AuthToken(authToken.accessToken))
        } else {
            initGooglePlayService(authToken)
        }
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

    fun getGooglePlayMusicPlaylists(): Observable<List<Playlist>> {
        if (googlePlayAuthToken == null || googlePlayMusicService == null) {
            return Observable.empty()
        }

        return Observable.just(googlePlayMusicService!!.playlistApi.listPlaylists())
    }
}