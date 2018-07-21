package com.vincent.playlisttransferutility.data.models.spotify

import com.google.gson.annotations.SerializedName
import com.spotify.sdk.android.authentication.AuthenticationResponse

class AuthToken(@SerializedName("access_token")
                val accessToken: String,
                @SerializedName("token_type")
                val tokenType: String,
                @SerializedName("expires_in")
                val expiresIn: Int) {

    companion object {
        fun fromAuthenticationResponse(authenticationResponse: AuthenticationResponse): AuthToken {
            return AuthToken(authenticationResponse.accessToken,
                    authenticationResponse.type.toString(),
                    authenticationResponse.expiresIn)
        }
    }
}