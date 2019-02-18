package com.vincent.playlisttransferutility.di.components

import com.vincent.playlisttransferutility.PlaylistTransferApplication
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.SharedPreferencesModule
import com.vincent.playlisttransferutility.di.modules.ActivityBuildersModule
import com.vincent.playlisttransferutility.di.modules.ContextModule
import com.vincent.playlisttransferutility.network.BaseNetworkModule
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayNetworkModule
import com.vincent.playlisttransferutility.network.spotify.SpotifyNetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    GsonModule::class,
    BaseNetworkModule::class,
    GooglePlayNetworkModule::class,
    SpotifyNetworkModule::class,
    RepositoryModule::class,
    ContextModule::class,
    DataSourceModule::class,
    SharedPreferencesModule::class,
    KeystoreModule::class
])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: PlaylistTransferApplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: PlaylistTransferApplication)
}