package com.vincent.playlisttransferutility.data.models.spotify.response

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistTracks(@SerializedName("total") val total: Int)