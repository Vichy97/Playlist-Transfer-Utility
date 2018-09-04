package com.vincent.playlisttransferutility

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.repository.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.data.sources.preferences.SharedPreferencesModule
import com.vincent.playlisttransferutility.network.BaseNetworkModule
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayMusicApi
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayMusicHeaderInterceptor
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayNetworkModule
import com.vincent.playlisttransferutility.network.spotify.SpotifyHeaderInterceptor
import com.vincent.playlisttransferutility.network.spotify.SpotifyNetworkModule
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.ui.googlelogin.di.GoogleLoginComponent
import com.vincent.playlisttransferutility.ui.googlelogin.di.GoogleLoginModule
import com.vincent.playlisttransferutility.ui.main.di.MainComponent
import com.vincent.playlisttransferutility.ui.main.di.MainModule
import com.vincent.playlisttransferutility.ui.playlistselection.di.PlaylistSelectionComponent
import com.vincent.playlisttransferutility.ui.playlistselection.di.PlaylistSelectionModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GsonModule::class,
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

    val gson: Gson
    val context: Context
    val repository: Repository
    val resourceProvider: ResourceProvider
    val schedulersProvider: SchedulersProvider
    val spotifyApi: SpotifyApi
    val spotifySpotifyHeaderInterceptor: SpotifyHeaderInterceptor
    val googlePlayMusicApi: GooglePlayMusicApi
    val googlePlayMusicHeaderInterceptor: GooglePlayMusicHeaderInterceptor
    val preferencesDataSource: PreferencesDataSource
    val sharedPreferences: SharedPreferences
    val keystore: KeyStore

    fun newMainComponent(mainModule: MainModule): MainComponent
    fun newGoogleLoginComponent(googleLoginModule: GoogleLoginModule): GoogleLoginComponent
    fun newPlaylistSelectionComponent(playlistSelectionModule: PlaylistSelectionModule):
            PlaylistSelectionComponent
}