package com.vincent.playlisttransferutility.data.spotify.models.request

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylistRequest(
        @SerializedName("name") val name: String,
        @SerializedName("public") val public: Boolean?,
        @SerializedName("collabrative") val collabrative: Boolean?,
        @SerializedName("description") val description: String?
)