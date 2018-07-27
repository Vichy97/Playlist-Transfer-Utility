package com.vincent.playlisttransferutility.data.models

import com.google.gson.annotations.SerializedName
import com.spotify.sdk.android.authentication.AuthenticationResponse

class AuthToken(@SerializedName("access_token")
                val accessToken: String,
                @SerializedName("token_type")
                val musicService: MusicService,
                @SerializedName("expires_in")
                val expiresIn: Long) {

    companion object {
        fun fromSpotifyAuthenticationResponse(authenticationResponse: AuthenticationResponse): AuthToken {
            return AuthToken(authenticationResponse.accessToken,
                    MusicService.SPOTIFY,
                    authenticationResponse.expiresIn.toLong())
        }

        fun fromGooglePlayAuthToken(authToken: svarzee.gps.gpsoauth.AuthToken): AuthToken {
            return AuthToken(authToken.token,
                    MusicService.GOOGLE_PLAY_MUSIC,
                    authToken.expiry)
        }
    }
}