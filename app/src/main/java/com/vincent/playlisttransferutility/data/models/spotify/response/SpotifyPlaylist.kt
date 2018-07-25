package com.vincent.playlisttransferutility.data.models.spotify.response

import com.google.gson.annotations.SerializedName

data class SpotifyPlaylist(
        @SerializedName("id") val id: String,
        @SerializedName("collaborative") val collaborative: Boolean,
        @SerializedName("images") val images: List<Image>,
        @SerializedName("name") val name: String,
        @SerializedName("public") val public: Boolean?,
        @SerializedName("tracks") val tracks: SpotifyPlaylistTracks
)