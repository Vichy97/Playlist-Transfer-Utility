package com.vincent.playlisttransferutility.data.models

import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyTrack

data class Track(val id: String,
                 val name: String,
                 val artist: String) {

    companion object {

        fun fromSpotifyTrack(spotifyTrack: SpotifyTrack): Track {
            return Track(spotifyTrack.uri,
                    spotifyTrack.name,
                    spotifyTrack.artists[0].name)
        }

        fun googlePlayMusicTrack(googlePlayMusicTrack: com.github.felixgail.gplaymusic.model.Track): Track {
            return Track(googlePlayMusicTrack.id,
                    googlePlayMusicTrack.title,
                    googlePlayMusicTrack.artist)
        }
    }
}