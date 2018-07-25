package com.vincent.playlisttransferutility.data.models

import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist

class Playlist(val id: String,
               val name: String,
               val coverArtUrl: String,
               val trackCount: Int,
               val musicService: MusicService) {

    companion object {
        fun fromSpotifyPlaylist(spotifyPlaylist: SpotifyPlaylist): Playlist {
            val coverArtUrl: String = spotifyPlaylist.images[spotifyPlaylist.images.size - 1].url

            return Playlist(spotifyPlaylist.id,
                    spotifyPlaylist.name, coverArtUrl,
                    spotifyPlaylist.tracks.total,
                    MusicService.SPOTIFY)
        }
    }
}