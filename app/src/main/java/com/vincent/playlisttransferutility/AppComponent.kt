package com.vincent.playlisttransferutility

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import com.vincent.playlisttransferutility.data.sources.DataSourceModule
import com.vincent.playlisttransferutility.data.sources.preferences.PreferencesDataSource
import com.vincent.playlisttransferutility.data.sources.preferences.SharedPreferencesModule
import com.vincent.playlisttransferutility.network.HeaderInterceptor
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import com.vincent.playlisttransferutility.network.api.NetworkModule
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.pages.googlelogin.di.GoogleLoginComponent
import com.vincent.playlisttransferutility.pages.googlelogin.di.GoogleLoginModule
import com.vincent.playlisttransferutility.pages.main.di.MainComponent
import com.vincent.playlisttransferutility.pages.main.di.MainModule
import com.vincent.playlisttransferutility.pages.playlistselection.di.PlaylistSelectionComponent
import com.vincent.playlisttransferutility.pages.playlistselection.di.PlaylistSelectionModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GsonModule::class, NetworkModule::class, ResourceProviderModule::class,
    RepositoryModule::class, ContextModule::class, DataSourceModule::class,
    OkHttpClientModule::class, SharedPreferencesModule::class, KeystoreModule::class])
@Singleton
interface AppComponent {

    companion object {
        lateinit var instance: AppComponent
    }

    val gson: Gson
    val context: Context
    val repository: Repository
    val resourceProvider: ResourceProvider
    val spotifyApi: SpotifyApi
    val spotifyHeaderInterceptor: HeaderInterceptor
    val preferencesDataSource: PreferencesDataSource
    val sharedPreferences: SharedPreferences
    val keystore: KeyStore

    fun newMainComponent(mainModule: MainModule): MainComponent
    fun newGoogleLoginComponent(googleLoginModule: GoogleLoginModule): GoogleLoginComponent
    fun newPlaylistSelectionComponent(playlistSelectionModule: PlaylistSelectionModule):
            PlaylistSelectionComponent
}