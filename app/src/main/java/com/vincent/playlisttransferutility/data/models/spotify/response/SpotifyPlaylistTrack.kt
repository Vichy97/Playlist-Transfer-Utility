package com.vincent.playlisttransferutility.data.models.spotify.response

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistTrack(@SerializedName("track") val track: SpotifyTrack)