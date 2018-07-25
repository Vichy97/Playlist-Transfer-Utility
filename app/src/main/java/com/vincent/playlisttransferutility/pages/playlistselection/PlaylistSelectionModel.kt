package com.vincent.playlisttransferutility.pages.playlistselection

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import io.reactivex.Observable

class PlaylistSelectionModel {

    val repository: Repository

    init {
        repository = DaggerPlaylistSelectionComponent.builder()
                .build()
                .getRepository()
    }

    fun getPlaylists(): Observable<List<Playlist>> {
        return repository.getSpotifyPlaylists().map{
            val playlists: ArrayList<Playlist> = ArrayList()

            for (spotifyPlaylist: SpotifyPlaylist in it) {
                playlists.add(Playlist.fromSpotifyPlaylist(spotifyPlaylist))
            }

            return@map playlists
        }
    }
}