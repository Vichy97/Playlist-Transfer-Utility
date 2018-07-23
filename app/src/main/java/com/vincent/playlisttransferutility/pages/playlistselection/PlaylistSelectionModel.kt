package com.vincent.playlisttransferutility.pages.playlistselection

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.spotify.Playlist
import io.reactivex.Observable

class PlaylistSelectionModel {

    val repository: Repository

    init {
        repository = DaggerPlaylistSelectionComponent.builder()
                .build()
                .getRepository()
    }

    fun getPlaylists(): Observable<List<Playlist>> {
        return repository.getSpotifyPlaylists()
    }
}