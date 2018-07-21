package com.vincent.playlisttransferutility.data.sources

import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(): PreferencesDataSource {
        return PreferencesDataSource()
    }
}