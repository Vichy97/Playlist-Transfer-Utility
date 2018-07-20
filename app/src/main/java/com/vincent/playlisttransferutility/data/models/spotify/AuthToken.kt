package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName

class AuthToken(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("token_type")
        val tokenType: String,
        @SerializedName("scope")
        val scope: String,
        @SerializedName("expires_in")
        val expiresIn: Int,
        @SerializedName("refresh_token")
        val refreshToken: String
)