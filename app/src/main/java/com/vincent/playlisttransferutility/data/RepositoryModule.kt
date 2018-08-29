package com.vincent.playlisttransferutility.data

import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(schedulersProvider: SchedulersProvider,
                          dataSource: PreferencesDataSource,
                          spotifyApi: SpotifyApi,
                          spotifyHeaderInterceptor: HeaderInterceptor,
                          gson: Gson): Repository {
        return Repository(schedulersProvider,
                dataSource,
                spotifyApi,
                spotifyHeaderInterceptor,
                gson)
    }
}