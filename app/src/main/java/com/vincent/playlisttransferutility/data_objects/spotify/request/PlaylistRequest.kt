package com.vincent.playlisttransferutility.data_objects.spotify.request

import com.google.gson.annotations.SerializedName

class PlaylistRequest(
        @SerializedName("name") val name: String,
        @SerializedName("public") val public: Boolean?,
        @SerializedName("collabrative") val collabrative: Boolean?,
        @SerializedName("description") val description: String?
)