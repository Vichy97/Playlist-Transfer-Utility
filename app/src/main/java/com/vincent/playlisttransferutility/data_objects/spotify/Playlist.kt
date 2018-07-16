package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class Playlist(
        @SerializedName("id") val id: String,
        @SerializedName("collabrative") val collabrative: Boolean,
        @SerializedName("images") val images: List<Image>,
        @SerializedName("name") val name: String,
        @SerializedName("public") val public: Boolean?,
        @SerializedName("tracks") val tracks: PagingObject<Track>
)