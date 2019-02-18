package com.vincent.playlisttransferutility.ui.googlelogin

import android.Manifest
import android.text.Editable
import android.util.Log
import androidx.navigation.NavController
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class GoogleLoginViewModel @Inject constructor(resourceProvider: ResourceProvider,
                                               rxProvider: RxProvider,
                                               navController: NavController,
                                               private val interactor: GoogleLoginInteractor)
    : BaseViewModel(resourceProvider, rxProvider, navController) {

    private companion object {
        val TAG: String = GoogleLoginViewModel::class.java.simpleName
    }

    private val permissionsSubject: BehaviorSubject<Array<String>> = BehaviorSubject.create()

    private var email: String = ""
    private var password: String = ""

    init {
        permissionsSubject.onNext(arrayOf(Manifest.permission.READ_PHONE_STATE))
    }

    fun getPermissionsEvents(): Observable<Array<String>> {
        return permissionsSubject
    }

    fun onSignInClicked() {
        compositeDisposable.add(interactor.loginToGooglePlay(email, password)
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