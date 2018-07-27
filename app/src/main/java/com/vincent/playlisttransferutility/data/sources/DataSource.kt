package com.vincent.playlisttransferutility.data.sources

import com.vincent.playlisttransferutility.data.models.AuthToken

interface DataSource {

    fun saveSpotifyAuthToken(authToken: AuthToken)

    fun getSpotifyAuthToken(): AuthToken?

    fun saveGooglePlayAuthToken(authToken: AuthToken)

    fun getGooglePlayAuthToken(): AuthToken?
}