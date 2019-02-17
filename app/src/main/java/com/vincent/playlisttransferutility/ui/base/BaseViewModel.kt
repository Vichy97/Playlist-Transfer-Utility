package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(protected val resourceProvider: ResourceProvider,
                             protected val rxProvider: RxProvider) : ViewModel() {

    protected val navigationSubject: PublishSubject<Int> = PublishSubject.create()
    protected val toastSubject: PublishSubject<String> = PublishSubject.create()
    protected val compositeDisposable = rxProvider.compositeDisposable()

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    fun getToastEvents(): Observable<String> {
        return toastSubject
    }

    fun getNavigationEvents(): Observable<Int> {
        return navigationSubject
    }
}