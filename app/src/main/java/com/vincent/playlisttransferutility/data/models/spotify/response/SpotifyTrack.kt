package com.vincent.playlisttransferutility.data.models.spotify.response

import com.google.gson.annotations.SerializedName

data class SpotifyTrack(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String,
        @SerializedName("artists") val artists: List<SpotifyArtist>
)