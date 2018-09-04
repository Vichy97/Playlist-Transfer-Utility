package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.repository.Repository
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.network.spotify.SpotifyHeaderInterceptor
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dataSource: PreferencesDataSource,
                          spotifyApi: SpotifyApi,
                          spotifySpotifyHeaderInterceptor: SpotifyHeaderInterceptor): Repository {
        return Repository(dataSource,
                spotifyApi,
                spotifySpotifyHeaderInterceptor)
    }
}