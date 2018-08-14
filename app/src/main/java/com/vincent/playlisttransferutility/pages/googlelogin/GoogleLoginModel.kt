package com.vincent.playlisttransferutility.pages.googlelogin

import com.github.felixgail.gplaymusic.util.TokenProvider
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.AuthToken
import io.reactivex.Completable

class GoogleLoginModel {

    private val repository: Repository

    init {
        repository = AppComponent.instance.repository
    }

    fun loginToGooglePlay(email: String, password: String): Completable {
        return Completable.create {
            val authToken: AuthToken = AuthToken.fromGooglePlayAuthToken(TokenProvider
                    .provideToken(email, password, ""))
            repository.setGooglePlayAuthToken(authToken)
            it.onComplete()
        }
    }
}