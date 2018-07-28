package com.vincent.playlisttransferutility.network.api

import com.github.felixgail.gplaymusic.api.GPlayMusic
import dagger.Module
import dagger.Provides
import svarzee.gps.gpsoauth.AuthToken
import javax.inject.Singleton

@Module
class GooglePlayModule {

    @Provides
    @Singleton
    fun provideGooglePlayMusicService(): GPlayMusic {
        return GPlayMusic.Builder().setAuthToken(AuthToken("")).build()
    }
}