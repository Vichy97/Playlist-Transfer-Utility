package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import com.vincent.playlisttransferutility.network.api.GooglePlayModule
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.network.api.SpotifyModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SpotifyModule::class, GooglePlayModule::class, DataSourceModule::class,
    OkHttpClientModule::class])
@Singleton
interface RepositoryComponent {

    val spotifyHeaderInterceptor: HeaderInterceptor
    val spotifyApi: SpotifyApi //TODO: maybe make this an app level dependency...
    val preferencesDataSource: PreferencesDataSource
    val googlePlayMusicService: GPlayMusic
}