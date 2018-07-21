package com.vincent.playlisttransferutility.data

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return Repository()
    }
}