package com.vincent.playlisttransferutility

import android.app.Application
import com.github.felixgail.gplaymusic.api.GPlayMusic
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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}