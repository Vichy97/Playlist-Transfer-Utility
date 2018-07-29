package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.vincent.playlisttransferutility.ContextModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class SharedPreferencesModule {

    companion object {
        const val SHARED_PREFERENCES_NAME: String = "shared_preferences"
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }
}