package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

data class Track(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String,
        @SerializedName("artists") val artists: List<Artist>
)