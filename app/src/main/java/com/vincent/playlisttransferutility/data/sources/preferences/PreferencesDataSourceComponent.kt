package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.SharedPreferences
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.keystore.KeystoreModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SharedPreferencesModule::class, KeystoreModule::class])
@Singleton
interface PreferencesDataSourceComponent {

    val sharedPreferences: SharedPreferences
    val keystore: KeyStore
}