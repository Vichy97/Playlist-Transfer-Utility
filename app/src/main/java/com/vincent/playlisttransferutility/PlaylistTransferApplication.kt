package com.vincent.playlisttransferutility

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary

class PlaylistTransferApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()

        setupLeakCanary()
        setupPrettyLogger()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
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