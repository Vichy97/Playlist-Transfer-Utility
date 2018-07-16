package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class PagingObject<T>(
        @SerializedName("items") val items: List<T>,
        @SerializedName("total") val total: Int
)