package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken

interface AuthTokenDataSource {

    fun getSpotifyAuthToken(): AuthToken?

    fun saveSpotifyAuthToken(authToken: AuthToken)

}