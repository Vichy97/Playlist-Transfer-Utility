package com.vincent.playlisttransferutility.utils.resources

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourceProviderModule {

    @Provides
    @Singleton
    fun provideResourceProvider(): ResourceProvider {
        return ResourceProvider()
    }
}