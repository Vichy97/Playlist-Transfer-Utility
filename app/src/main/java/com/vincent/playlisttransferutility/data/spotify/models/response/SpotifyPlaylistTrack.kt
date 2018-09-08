package com.vincent.playlisttransferutility.data.spotify.models.response

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistTrack(@SerializedName("track") val track: SpotifyTrack)