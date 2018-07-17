package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

data class PagingObject<T>(
        @SerializedName("items") val items: List<T>,
        @SerializedName("total") val total: Int
)