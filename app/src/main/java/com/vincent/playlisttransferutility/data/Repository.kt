package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.github.felixgail.gplaymusic.model.Playlist
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class Repository  {

    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private var googlePlayMusicService: GPlayMusic? = null
    private val spotifyHeaderInterceptor: HeaderInterceptor
    private var spotifyAuthToken: AuthToken? = null
    private var googlePlayAuthToken: AuthToken? = null

    private var spotifyPlaylists: List<SpotifyPlaylist>? = null
    private var googlePlayMusicPlaylists: List<Playlist>? = null

    init {
        val repositoryComponent: RepositoryComponent = DaggerRepositoryComponent.builder().build()
        dataSource = repositoryComponent.preferencesDataSource
        spotifyApi = repositoryComponent.spotifyApi
        spotifyHeaderInterceptor = repositoryComponent.spotifyHeaderInterceptor

        googlePlayAuthToken = dataSource.getGooglePlayAuthToken()
        if (googlePlayAuthToken == null) {
            googlePlayAuthToken = AuthToken("", MusicService.GOOGLE_PLAY_MUSIC, -1)
        } else {
            initGooglePlayService(googlePlayAuthToken!!)
        }
        //TODO: auth token expires so fast... might not be worth storing
        spotifyAuthToken = AuthToken("", MusicService.SPOTIFY, -1)
    }

    private fun initGooglePlayService(googlePlayAuthToken: AuthToken): Completable {
        return Completable.create {
            googlePlayMusicService = GPlayMusic.Builder()
                    .setAuthToken(svarzee.gps.gpsoauth.AuthToken(googlePlayAuthToken.accessToken))
                    .setAndroidID("35803508140637")
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
        return Single.just(googlePlayAuthToken)
    }

    fun getSpotifyPlaylists(): Observable<List<SpotifyPlaylist>> {
        if (spotifyAuthToken == null || spotifyAuthToken!!.accessToken.isEmpty()) {
            return Observable.empty()
        }

        if (spotifyPlaylists != null) {
            return Observable.just(spotifyPlaylists)
        }

        return spotifyApi.getAllPlaylists(null, null)
                .flatMapObservable {
                    spotifyPlaylists = it.items
                    Observable.just(spotifyPlaylists)
                }
    }

    fun getGooglePlayMusicPlaylists(): Observable<List<Playlist>> {
        if (googlePlayAuthToken == null || googlePlayMusicService == null) {
            return Observable.empty()
        }

        if (googlePlayMusicPlaylists != null) {
            return Observable.just(googlePlayMusicPlaylists)
        }

        return Observable.create {
            googlePlayMusicPlaylists = googlePlayMusicService!!.playlistApi.listPlaylists()
            it.onNext(googlePlayMusicPlaylists!!)
        }
    }
}