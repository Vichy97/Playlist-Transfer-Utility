package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class Image(
        @SerializedName("width") val width: Int?,
        @SerializedName("height") val height: Int?,
        @SerializedName("url") val url: String
)