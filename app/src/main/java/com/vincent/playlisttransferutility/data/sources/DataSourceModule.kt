package com.vincent.playlisttransferutility.data.sources

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(sharedPreferences: SharedPreferences,
                                     gson: Gson,
                                     keystore: KeyStore): PreferencesDataSource {
        return PreferencesDataSource(sharedPreferences,
                gson,
                keystore)
    }
}