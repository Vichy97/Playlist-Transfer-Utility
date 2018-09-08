package com.vincent.playlisttransferutility.data.spotify.models.response

import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName("width") val width: Int?,
        @SerializedName("height") val height: Int?,
        @SerializedName("url") val url: String
)