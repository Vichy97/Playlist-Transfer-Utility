package com.vincent.playlisttransferutility.data.spotify.models.response

import com.google.gson.annotations.SerializedName
import com.vincent.playlisttransferutility.data.spotify.models.response.SpotifyArtist

data class SpotifyTrack(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String,
        @SerializedName("artists") val artists: List<SpotifyArtist>
)