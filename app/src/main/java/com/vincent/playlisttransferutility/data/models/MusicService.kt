package com.vincent.playlisttransferutility.data.models

import com.google.gson.annotations.SerializedName

enum class MusicService(@Transient private val displayName: String) {
    @SerializedName("spotify")
    SPOTIFY("Spotify"),
    @SerializedName("google_play_music")
    GOOGLE_PLAY_MUSIC("Google Play Music"),
    @SerializedName("apple_music")
    APPLE_MUSIC("Apple Music");

    override fun toString(): String {
        return displayName
    }
}