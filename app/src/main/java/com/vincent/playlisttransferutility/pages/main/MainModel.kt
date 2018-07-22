package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import io.reactivex.Observable

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

    fun getSpotifyAuthToken(): Observable<AuthToken> {
        return repository.getSpotifyAuthToken()
    }
}