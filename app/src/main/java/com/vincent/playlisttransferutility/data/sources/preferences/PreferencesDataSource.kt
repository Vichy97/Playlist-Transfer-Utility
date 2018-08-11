package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.ContextModule
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.sources.DataSource

class PreferencesDataSource : DataSource {

    companion object {
        private const val SPOTIFY_AUTH_TOKEN_KEY: String = "spotify_auth_token_key"
        private const val SPOTIFY_KEYSTORE_ALIAS: String = "spotify_keystore"
        private const val GOOGLE_PLAY_AUTH_TOKEN_KEY: String = "google_play_auth_token_key"
        private const val GOOGLE_PLAY_KEYSTORE_ALIAS: String = "google_play_keystore"
    }

    private val sharedPreferences: SharedPreferences
    private val gson: Gson
    private val keystore: KeyStore

    init {
        keystore = AppComponent.instance.keystore
        sharedPreferences = AppComponent.instance.sharedPreferences
        gson = AppComponent.instance.gson
    }

    override fun saveSpotifyAuthToken(authToken: AuthToken) {
        val jsonString: String = gson.toJson(authToken)
        val encryptedString: String = keystore.encrypt(jsonString, SPOTIFY_KEYSTORE_ALIAS)
        sharedPreferences.edit().putString(SPOTIFY_AUTH_TOKEN_KEY, encryptedString).apply()
    }

    override fun getSpotifyAuthToken(): AuthToken? {
        val encryptedString: String = sharedPreferences.getString(SPOTIFY_AUTH_TOKEN_KEY, "")
        if (encryptedString.isEmpty()) {
            return null
        }

        val jsonString: String = keystore.decrypt(encryptedString, SPOTIFY_KEYSTORE_ALIAS)
        return gson.fromJson(jsonString, AuthToken::class.java)
    }

    override fun saveGooglePlayAuthToken(authToken: AuthToken) {
        val jsonString: String = gson.toJson(authToken)
        val encryptedString: String = keystore.encrypt(jsonString, GOOGLE_PLAY_KEYSTORE_ALIAS)
        sharedPreferences.edit().putString(GOOGLE_PLAY_AUTH_TOKEN_KEY, encryptedString).apply()
    }

    override fun getGooglePlayAuthToken(): AuthToken? {
        val encryptedString: String = sharedPreferences.getString(GOOGLE_PLAY_AUTH_TOKEN_KEY, "")
        if (encryptedString.isEmpty()) {
            return null
        }

        val jsonString: String = keystore.decrypt(encryptedString, GOOGLE_PLAY_KEYSTORE_ALIAS)
        return gson.fromJson(jsonString, AuthToken::class.java)
    }

}