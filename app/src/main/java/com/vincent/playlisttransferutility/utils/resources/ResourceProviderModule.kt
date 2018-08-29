package com.vincent.playlisttransferutility.utils.resources

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourceProviderModule {

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }
}