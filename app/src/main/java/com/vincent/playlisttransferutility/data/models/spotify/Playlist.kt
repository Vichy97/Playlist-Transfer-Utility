package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

data class Playlist(
        @SerializedName("id") val id: String,
        @SerializedName("collabrative") val collabrative: Boolean,
        @SerializedName("images") val images: List<Image>,
        @SerializedName("name") val name: String,
        @SerializedName("public") val public: Boolean?,
        @SerializedName("tracks") val tracks: PagingObject<Track>
)