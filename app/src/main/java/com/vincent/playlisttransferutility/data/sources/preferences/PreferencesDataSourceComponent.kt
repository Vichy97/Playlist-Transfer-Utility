package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PreferencesDataSourceModule::class, GsonModule::class, KeystoreModule::class])
@Singleton
interface PreferencesDataSourceComponent {

    fun getSharedPreferences(): SharedPreferences
    fun getGson(): Gson
    fun getKeystore(): KeyStore
}