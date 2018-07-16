package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class User(
        @SerializedName("id") val id: String,
        @SerializedName("uri") val uri: String,
        @SerializedName("display_name") val displayName: String?,
        @SerializedName("images") val profileImage: List<Image>
)