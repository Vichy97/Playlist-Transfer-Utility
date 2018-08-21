package com.vincent.playlisttransferutility

import android.app.Application
import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary

class PlaylistTransferApplication : Application() {

    companion object {
        //TODO: find a way to inject this...
        var googlePlayMusicService: GPlayMusic? = null
    }

    override fun onCreate() {
        super.onCreate()

        AppComponent.instance = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()

        setupLeakCanary()
        setupPrettyLogger()
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