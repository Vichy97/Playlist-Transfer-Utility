package com.vincent.playlisttransferutility.di

import com.vincent.playlisttransferutility.PlaylistTransferApplication
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.SharedPreferencesModule
import com.vincent.playlisttransferutility.network.BaseNetworkModule
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayNetworkModule
import com.vincent.playlisttransferutility.network.spotify.SpotifyNetworkModule
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModule
import com.vincent.playlisttransferutility.ui.main.MainModule
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import com.vincent.playlisttransferutility.utils.rx.SchedulersProviderModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    GoogleLoginModule::class,
    MainModule::class,
    PlaylistSelectionModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    BuildersModule::class,
    GsonModule::class,
    BaseNetworkModule::class,
    GooglePlayNetworkModule::class,
    SpotifyNetworkModule::class,
    ResourceProviderModule::class,
    SchedulersProviderModule::class,
    RepositoryModule::class,
    ContextModule::class,
    DataSourceModule::class,
    SharedPreferencesModule::class,
    KeystoreModule::class])
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