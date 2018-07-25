package com.vincent.playlisttransferutility.data.models.spotify.response

import com.google.gson.annotations.SerializedName

data class SpotifyArtist(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String
)