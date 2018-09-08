package com.vincent.playlisttransferutility.di

import android.content.Context
import com.vincent.playlisttransferutility.PlaylistTransferApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(app: PlaylistTransferApplication): Context {
        return app.applicationContext
    }
}