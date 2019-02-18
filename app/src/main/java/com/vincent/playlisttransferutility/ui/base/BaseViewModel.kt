package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(protected val resourceProvider: ResourceProvider,
                             protected val rxProvider: RxProvider,
                             protected val navController: NavController) : ViewModel() {

    protected val toastSubject: PublishSubject<String> = PublishSubject.create()
    protected val compositeDisposable = rxProvider.compositeDisposable()

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    fun getToastEvents(): Observable<String> {
        return toastSubject
    }
}