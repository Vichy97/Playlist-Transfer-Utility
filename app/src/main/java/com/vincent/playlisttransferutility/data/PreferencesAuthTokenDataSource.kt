package com.vincent.playlisttransferutility.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vincent.playlisttransferutility.data.models.spotify.AuthToken

class PreferencesAuthTokenDataSource : AuthTokenDataSource {

    companion object {
        const val SHARED_PREFERENCES_NAME: String = "shared_preferences"
        //TODO: get key from android keystore
        const val SPOTIFY_AUTH_TOKEN_KEY: String = "spotify_auth_token_key"
    }

    //TODO: have dependencies injected at the app level
    lateinit var sharedPreferences: SharedPreferences
    lateinit var gson: Gson
    lateinit var context: Context

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        gson = GsonBuilder().create()
    }

    override fun getSpotifyAuthToken(): AuthToken? {
        val jsonString: String = sharedPreferences.getString(SPOTIFY_AUTH_TOKEN_KEY, null)
        return gson.fromJson(jsonString, AuthToken::class.java)
    }

    override fun saveSpotifyAuthToken(authToken: AuthToken) {
        val jsonString: String = gson.toJson(authToken)
        sharedPreferences.edit().putString(SPOTIFY_AUTH_TOKEN_KEY, jsonString).apply()
    }
}