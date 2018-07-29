package com.vincent.playlisttransferutility.data.sources.preferences

import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [GsonModule::class, KeystoreModule::class])
@Singleton
interface PreferencesDataSourceComponent {

    val gson: Gson
    val keystore: KeyStore
}