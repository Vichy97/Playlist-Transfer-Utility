package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class Artist(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("name") val name: String
)