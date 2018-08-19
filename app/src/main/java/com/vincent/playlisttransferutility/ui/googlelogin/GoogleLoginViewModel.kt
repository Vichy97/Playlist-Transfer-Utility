package com.vincent.playlisttransferutility.ui.googlelogin

import android.text.Editable
import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.ui.googlelogin.di.GoogleLoginModule
import io.reactivex.disposables.CompositeDisposable

class GoogleLoginViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable

    private val model: GoogleLoginModel
    private var email: String = ""
    private var password: String = ""

    init {
        compositeDisposable = CompositeDisposable()
        model = AppComponent.instance
                .newGoogleLoginComponent(GoogleLoginModule())
                .googleLoginModel
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    fun onSignInClicked() {
        model.loginToGooglePlay(email, password)
    }

    fun onEmailChanged(editable: Editable) {
       email = editable.toString()
    }

    fun onPasswordChanged(editable: Editable) {
        password = editable.toString()
    }
}