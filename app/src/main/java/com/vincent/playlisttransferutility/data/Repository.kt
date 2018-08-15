package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.github.felixgail.gplaymusic.model.Playlist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.PlaylistTransferApplication.Companion.googlePlayMusicService
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Track
import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyPlaylistRequest
import com.vincent.playlisttransferutility.data.models.spotify.response.*
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class Repository {

    private val schedulersProvider: SchedulersProvider
    private val dataSource: DataSource
    private val spotifyApi: SpotifyApi
    private val spotifyHeaderInterceptor: HeaderInterceptor
    private val gson: Gson
    private var spotifyAuthToken: AuthToken?
    private var googlePlayAuthToken: AuthToken? //TODO: non-nullable

    //TODO: make cache class
    private var spotifyPlaylists: MutableList<SpotifyPlaylist> = mutableListOf()
    private var googlePlayMusicPlaylists: MutableList<Playlist> = mutableListOf()
    private var spotifyUser: SpotifyUser? = null

    init {
        schedulersProvider = AppComponent.instance.schedulersProvider
        dataSource = AppComponent.instance.preferencesDataSource
        spotifyApi = AppComponent.instance.spotifyApi
        spotifyHeaderInterceptor = AppComponent.instance.spotifyHeaderInterceptor
        gson = AppComponent.instance.gson

        googlePlayAuthToken = dataSource.getGooglePlayAuthToken()
        if (googlePlayAuthToken == null) {
            googlePlayAuthToken = AuthToken("", MusicService.GOOGLE_PLAY_MUSIC, -1)
        } else {
            initGooglePlayService(googlePlayAuthToken!!)
                    .subscribeOn(schedulersProvider.io())
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

    //region Auth Tokens
    fun setSpotifyAuthToken(authToken: AuthToken): Completable {
        spotifyAuthToken = authToken
        spotifyHeaderInterceptor.setAccessToken(authToken.accessToken)
        dataSource.saveSpotifyAuthToken(authToken)

        return Completable.fromSingle<SpotifyUser>(spotifyApi.getCurrentUser().doOnSuccess {
            spotifyUser = it
        })
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
                    .subscribeOn(schedulersProvider.io())
                    .subscribe()
        }
        dataSource.saveGooglePlayAuthToken(authToken)
    }

    fun getGooglePlayAuthToken(): Single<AuthToken> {
        return Single.just(googlePlayAuthToken)
    }
    //endregion Auth Tokens

    //region Playlists
    fun getSpotifyPlaylists(): Single<List<SpotifyPlaylist>> {
        if (spotifyAuthToken == null || spotifyAuthToken!!.accessToken.isEmpty()) {
            return Single.error(Exception("Invalid Auth Token"))
        }

        if (spotifyPlaylists.isNotEmpty()) {
            return Single.just(spotifyPlaylists)
        }

        return spotifyApi.getAllPlaylists(null, null)
                .map {
                    spotifyPlaylists = it.items.toMutableList()
                    return@map spotifyPlaylists
                }
    }

    fun getGooglePlayMusicPlaylists(): Single<List<Playlist>> {
        if (googlePlayAuthToken == null || googlePlayMusicService == null) {
            return Single.error(Exception("Invalid Auth Token"))
        }

        if (googlePlayMusicPlaylists.isNotEmpty()) {
            return Single.just(googlePlayMusicPlaylists)
        }

        return Single.fromCallable {
            val jsonString: String = gson.toJson(googlePlayMusicService!!.playlistApi.listPlaylists())
            googlePlayMusicPlaylists = gson.fromJson(jsonString, object : TypeToken<List<Playlist>>() {}.type)
            return@fromCallable googlePlayMusicPlaylists
        }
    }

    fun createSpotifyPlaylist(name: String): Completable {
        if (spotifyUser == null) {
            return Completable.error(Exception("Spotify User Not Found"))
        }

        val playlistRequest: SpotifyPlaylistRequest = SpotifyPlaylistRequest(name, null, null, null)
        return Completable.fromSingle(spotifyApi.createPlaylist(spotifyUser!!.id, playlistRequest)
                .doOnSuccess {
                    spotifyPlaylists.add(it)
                })
    }

    fun addTracksToSpotifyPlaylist(playlistId: String, tracks: List<SpotifyTrack>): Completable {
        if (spotifyUser == null) {
            return Completable.error(Exception("Spotify User Not Found"))
        }

        val trackUris: MutableList<String> = mutableListOf()
        for (track: SpotifyTrack in tracks) {
            trackUris.add(track.uri)
        }
        return Completable.fromSingle(spotifyApi.addTracksToPlaylist(spotifyUser!!.id, playlistId, trackUris))
    }
    //endregion Playlists

    //region Tracks
    fun getSpotifyTracks(playlistId: String): Single<List<SpotifyTrack>> {
        if (spotifyAuthToken == null || spotifyAuthToken!!.accessToken.isEmpty()) {
            return Single.error(Exception("Invalid Auth Token"))
        }

        if (spotifyUser == null) {
            return Single.error(Exception("Spotify User Not Found"))
        }

        return spotifyApi.getTracksForPlaylist(spotifyUser!!.id, playlistId, 200).map {
            val tracks: MutableList<SpotifyTrack> = mutableListOf()

            for (playlistTrack: SpotifyPlaylistTrack in it.tracks.items) {
                tracks.add(playlistTrack.track)
            }

            return@map tracks
        }
    }

    fun searchSpotifyTracks(tracks: List<Track>): Observable<SpotifyPagingObject<SpotifyTrack>> {
        return Observable.fromIterable(tracks)
                .flatMapSingle {
                    val query: String = it.name + " " + it.artist

                    return@flatMapSingle spotifyApi.searchTracks(query, "track", 1)
                }

    }
    //endregion Tracks
}