package com.vincent.playlisttransferutility.ui.googlelogin

import android.Manifest
import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

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

    private companion object {
        val TAG: String = GoogleLoginViewModel::class.java.simpleName
    }

    private val permissionsSubject: BehaviorSubject<Array<String>>

    private var email: String = ""
    private var password: String = ""

    init {
        permissionsSubject = BehaviorSubject.create()
        permissionsSubject.onNext(arrayOf(Manifest.permission.READ_PHONE_STATE))
    }

    fun getPermissionsEvents(): Observable<Array<String>> {
        return permissionsSubject
    }

    fun onSignInClicked() {
        compositeDisposable.add(model.loginToGooglePlay(email, password)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe(this::onSignInSuccess, this::onSignInError))
    }

    private fun onSignInSuccess() {

    }

    private fun onSignInError(e: Throwable) {
        Log.e(TAG, "Google Login Failed", e)
//        toastSubject.onNext(e.message!!)
    }

    fun onEmailChanged(editable: Editable) {
        email = editable.toString()
    }

    fun onPasswordChanged(editable: Editable) {
        password = editable.toString()
    }
}