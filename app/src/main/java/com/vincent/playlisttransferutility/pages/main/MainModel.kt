package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Single

class MainModel {

    private val repository: Repository

    init {
        repository = DaggerMainComponent.builder()
                .build()
                .getRepository()
    }

    fun saveSpotifyAuthToken(authToken: AuthToken) {
        repository.setSpotifyAuthToken(authToken)
    }

    fun getSpotifyAuthToken(): Single<AuthToken> {
        return repository.getSpotifyAuthToken()
    }

    fun saveGooglePlayAuthToken(authToken: AuthToken) {
        repository.setGooglePlayAuthToken(authToken)
    }

    fun getGooglePlayAuthToken(): Single<AuthToken> {
        return repository.getGooglePlayAuthToken()
    }
}