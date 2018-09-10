package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
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
    fun provideSpotifyRepository(dataSource: PreferencesDataSource,
                          spotifyApi: SpotifyApi,
                          spotifySpotifyHeaderInterceptor: SpotifyHeaderInterceptor): SpotifyRepository {
        return SpotifyRepository(dataSource,
                spotifyApi,
                spotifySpotifyHeaderInterceptor)
    }
}