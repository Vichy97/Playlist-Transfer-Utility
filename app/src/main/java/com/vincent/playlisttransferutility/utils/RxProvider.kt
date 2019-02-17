package com.vincent.playlisttransferutility.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxProvider @Inject constructor() {

    fun uiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    fun computationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}