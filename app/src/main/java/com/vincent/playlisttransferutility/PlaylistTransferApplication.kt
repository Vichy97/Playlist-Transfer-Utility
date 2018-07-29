package com.vincent.playlisttransferutility

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class PlaylistTransferApplication : Application() {
    
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