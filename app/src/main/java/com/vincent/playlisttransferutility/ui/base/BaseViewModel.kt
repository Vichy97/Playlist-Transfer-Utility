package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(protected val resourceProvider: ResourceProvider,
                             protected val schedulersProvider: SchedulersProvider,
                             protected val compositeDisposable: CompositeDisposable) : ViewModel() {

    protected val navigationSubject: PublishSubject<Int> = PublishSubject.create()
    protected val toastSubject: PublishSubject<String> = PublishSubject.create()

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