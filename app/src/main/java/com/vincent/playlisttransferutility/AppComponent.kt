package com.vincent.playlisttransferutility

import android.content.Context
import android.content.SharedPreferences
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.data.sources.preferences.SharedPreferencesModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SharedPreferencesModule::class, ResourceProviderModule::class, RepositoryModule::class, ContextModule::class])
@Singleton
interface AppComponent {

    companion object {
        lateinit var instance: AppComponent
    }

    val context: Context
    val repository: Repository
    val sharedPreferences: SharedPreferences
    val resourceProvider: ResourceProvider
}