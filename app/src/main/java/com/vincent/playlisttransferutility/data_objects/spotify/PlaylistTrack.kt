package com.vincent.playlisttransferutility.data_objects.spotify

import com.google.gson.annotations.SerializedName

class PlaylistTrack(
        @SerializedName("track") val track: Track
)