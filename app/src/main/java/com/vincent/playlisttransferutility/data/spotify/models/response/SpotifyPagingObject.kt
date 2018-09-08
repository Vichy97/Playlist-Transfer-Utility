package com.vincent.playlisttransferutility.data.spotify.models.response

import com.google.gson.annotations.SerializedName

data class SpotifyPagingObject<T>(
        @SerializedName("items") val items: List<T>,
        @SerializedName("total") val total: Int,
        @SerializedName("offset") val offset: Int,
        @SerializedName("next") val next: String?
)