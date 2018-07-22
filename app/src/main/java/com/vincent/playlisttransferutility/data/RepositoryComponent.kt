package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.network.api.SpotifyModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SpotifyModule::class, DataSourceModule::class, OkHttpClientModule::class])
@Singleton
interface RepositoryComponent {

    fun getSpotifyHeaderInterceptor(): HeaderInterceptor
    fun getSpotifyApi(): SpotifyApi
    fun getAuthTokenDataSource(): PreferencesDataSource
}