package com.vincent.playlisttransferutility.data

import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataSourceModule::class, OkHttpClientModule::class])
@Singleton
interface RepositoryComponent {

    val spotifyHeaderInterceptor: HeaderInterceptor
    val preferencesDataSource: PreferencesDataSource
}