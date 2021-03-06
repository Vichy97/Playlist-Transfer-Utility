package com.vincent.playlisttransferutility.data.spotify

import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Track
import com.vincent.playlisttransferutility.data.spotify.models.request.SpotifyPlaylistRequest
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.data.spotify.models.response.*
import com.vincent.playlisttransferutility.network.spotify.SpotifyHeaderInterceptor
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class SpotifyRepository(private val dataSource: DataSource,
                        private val spotifyApi: SpotifyApi,
                        private val spotifySpotifyHeaderInterceptor: SpotifyHeaderInterceptor) {

    private var spotifyAuthToken: AuthToken
    private var spotifyUser: SpotifyUser? = null

    init {
        spotifyAuthToken = AuthToken("", MusicService.SPOTIFY, -1)
    }

    //region Auth Tokens
    fun setSpotifyAuthToken(authToken: AuthToken): Completable {
        spotifyAuthToken = authToken
        spotifySpotifyHeaderInterceptor.setAccessToken(authToken.accessToken)

        return Completable.fromSingle<SpotifyUser>(spotifyApi
                .getCurrentUser()
                .doOnSuccess {
                    spotifyUser = it
                })
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return Single.just(spotifyAuthToken)
    }
    //endregion Auth Tokens

    //region Playlists
    fun getSpotifyPlaylists(): Single<List<SpotifyPlaylist>> {
        return spotifyApi.getAllPlaylists(null, null)
                .map {
                    return@map it.items.toMutableList()
                }
    }

    fun createSpotifyPlaylist(name: String): Completable {
        if (spotifyUser == null) {
            return Completable.error(Exception("Spotify User Not Found"))
        }

        val playlistRequest: SpotifyPlaylistRequest = SpotifyPlaylistRequest(name, null, null, null)
        return Completable.fromSingle(spotifyApi.createPlaylist(spotifyUser!!.id, playlistRequest))}

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
        if (spotifyUser == null) {
            return Single.error(Exception("Spotify User Not Found"))
        }

        return spotifyApi.getTracksForPlaylist(spotifyUser!!.id, playlistId, 200)
                .map {
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