package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable: CompositeDisposable
    protected val resourceProvider: ResourceProvider
    protected val schedulersProvider: SchedulersProvider

    protected val navigationSubject: PublishSubject<Int>
    protected val toastSubject: PublishSubject<String>

    init {
        compositeDisposable = CompositeDisposable()
        resourceProvider = AppComponent.instance.resourceProvider
        schedulersProvider = AppComponent.instance.schedulersProvider

        navigationSubject = PublishSubject.create()
        toastSubject = PublishSubject.create()
    }

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