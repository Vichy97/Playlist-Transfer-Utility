package com.vincent.playlisttransferutility

import android.content.Context
import com.github.felixgail.gplaymusic.api.GPlayService
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.network.api.NetworkModule
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GsonModule::class, NetworkModule::class, ResourceProviderModule::class,
    RepositoryModule::class, ContextModule::class])
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
    val googlePlayService: GPlayService
}