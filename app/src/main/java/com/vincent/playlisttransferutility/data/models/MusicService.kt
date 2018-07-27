package com.vincent.playlisttransferutility.data.models

import com.google.gson.annotations.SerializedName

enum class MusicService {
    @SerializedName("spotify")
    SPOTIFY,
    @SerializedName("google_play_music")
    GOOGLE_PLAY_MUSIC,
    @SerializedName("apple_music")
    APPLE_MUSIC
}