package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.github.felixgail.gplaymusic.model.Playlist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Repository  {

    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private var googlePlayMusicService: GPlayMusic? = null
    private val spotifyHeaderInterceptor: HeaderInterceptor
    private val gson: Gson
    private var spotifyAuthToken: AuthToken?
    private var googlePlayAuthToken: AuthToken? //TODO: non-nullable

    //TODO: make cache class
    private var spotifyPlaylists: List<SpotifyPlaylist>? = null
    private var googlePlayMusicPlaylists: ArrayList<Playlist>? = null

    init {
        val repositoryComponent: RepositoryComponent = DaggerRepositoryComponent.builder().build()
        dataSource = repositoryComponent.preferencesDataSource
        spotifyApi = AppComponent.instance.spotifyApi
        spotifyHeaderInterceptor = repositoryComponent.spotifyHeaderInterceptor
        gson = AppComponent.instance.gson

        googlePlayAuthToken = dataSource.getGooglePlayAuthToken()
        if (googlePlayAuthToken == null) {
            googlePlayAuthToken = AuthToken("", MusicService.GOOGLE_PLAY_MUSIC, -1)
        } else {
            initGooglePlayService(googlePlayAuthToken!!)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
        //TODO: auth token expires so fast... might not be worth storing (it would need a timestamp)
        spotifyAuthToken = AuthToken("", MusicService.SPOTIFY, -1)
    }

    private fun initGooglePlayService(googlePlayAuthToken: AuthToken): Completable {
        return Completable.create {
            googlePlayMusicService = GPlayMusic.Builder()
                    .setAuthToken(svarzee.gps.gpsoauth.AuthToken(googlePlayAuthToken.accessToken, -1))
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
                    .subscribeOn(Schedulers.io())
                    .subscribe()
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

        return Observable.fromCallable {
            val jsonString: String = gson.toJson(googlePlayMusicService!!.playlistApi.listPlaylists())
            googlePlayMusicPlaylists = gson.fromJson(jsonString, object : TypeToken<List<Playlist>>() {}.type)
            return@fromCallable googlePlayMusicPlaylists
        }
    }
}