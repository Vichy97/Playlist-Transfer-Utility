package com.vincent.playlisttransferutility.data.sources

import com.vincent.playlisttransferutility.data.models.spotify.AuthToken

interface DataSource {

    fun getSpotifyAuthToken(): AuthToken?
    fun saveSpotifyAuthToken(authToken: AuthToken)
}