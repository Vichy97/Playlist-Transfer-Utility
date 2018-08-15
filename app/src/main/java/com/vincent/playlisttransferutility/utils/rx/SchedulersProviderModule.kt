package com.vincent.playlisttransferutility.utils.rx

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulersProviderModule {

    @Provides
    @Singleton
    fun provideSchedulersProvider(): SchedulersProvider {
        return SchedulersProvider()
    }
}