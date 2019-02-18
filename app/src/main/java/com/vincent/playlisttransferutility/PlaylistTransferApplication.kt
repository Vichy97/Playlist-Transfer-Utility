package com.vincent.playlisttransferutility

import android.app.Activity
import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.vincent.playlisttransferutility.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class PlaylistTransferApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

       DaggerAppComponent.builder()
               .application(this)
               .build()
               .inject(this)

        setupLeakCanary()
        setupPrettyLogger()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun setupPrettyLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy
                .newBuilder()
                .showThreadInfo(false)
                .tag("PLAYLIST_TRANSFER")
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}