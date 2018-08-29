package com.vincent.playlisttransferutility.ui.googlelogin

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider

class GoogleLoginViewModel(resourceProvider: ResourceProvider,
                           schedulersProvider: SchedulersProvider,
                           private val model: GoogleLoginModel) : BaseViewModel(resourceProvider, schedulersProvider) {

    class Factory(private val resourceProvider: ResourceProvider,
                  private val schedulersProvider: SchedulersProvider,
                  private val model: GoogleLoginModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GoogleLoginViewModel(resourceProvider, schedulersProvider, model) as T
        }
    }

    private var email: String = ""
    private var password: String = ""

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