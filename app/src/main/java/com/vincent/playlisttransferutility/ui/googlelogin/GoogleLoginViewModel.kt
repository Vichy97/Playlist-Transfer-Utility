package com.vincent.playlisttransferutility.ui.googlelogin

import android.Manifest
import android.text.Editable
import android.util.Log
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class GoogleLoginViewModel(resourceProvider: ResourceProvider,
                           rxProvider: RxProvider,
                           private val model: GoogleLoginModel) : BaseViewModel(resourceProvider, rxProvider) {

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
                .subscribeOn(rxProvider.ioScheduler())
                .observeOn(rxProvider.uiScheduler())
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