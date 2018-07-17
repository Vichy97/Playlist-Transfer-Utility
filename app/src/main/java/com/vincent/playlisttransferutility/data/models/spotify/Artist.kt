package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

data class Artist(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String
)