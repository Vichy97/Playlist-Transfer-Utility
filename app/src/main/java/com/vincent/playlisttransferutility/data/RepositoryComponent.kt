package com.vincent.playlisttransferutility.data

import com.github.felixgail.gplaymusic.api.GPlayMusic
import com.google.gson.Gson
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
    OkHttpClientModule::class, GsonModule::class])
@Singleton //TODO: custom scope (maybe sub component of app component)
interface RepositoryComponent {

    val spotifyHeaderInterceptor: HeaderInterceptor //TODO: also maybe app level
    val spotifyApi: SpotifyApi //TODO: make app level
    val preferencesDataSource: PreferencesDataSource
    val googlePlayMusicService: GPlayMusic
    val gson: Gson //TODO: make app level
}