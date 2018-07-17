package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

data class PlaylistTrack(
        @SerializedName("track") val track: Track
)