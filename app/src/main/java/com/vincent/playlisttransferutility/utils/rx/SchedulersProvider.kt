package com.vincent.playlisttransferutility.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProvider {

    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun computation(): Scheduler {
        return Schedulers.computation()
    }
}