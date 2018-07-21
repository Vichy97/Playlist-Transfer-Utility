package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PreferencesDataSourceModule::class, GsonModule::class])
@Singleton
interface PreferencesDataSourceComponent {

    fun getSharedPreferences(): SharedPreferences
    fun getGson(): Gson
}