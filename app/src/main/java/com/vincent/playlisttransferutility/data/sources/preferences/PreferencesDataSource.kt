package com.vincent.playlisttransferutility.data.sources.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vincent.playlisttransferutility.ContextModule
import com.vincent.playlisttransferutility.PlaylistTransferApplication
import com.vincent.playlisttransferutility.data.keystore.KeyStore
import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.data.sources.DataSource

class PreferencesDataSource : DataSource {

    companion object {
        //TODO: get key from android keystore
        const val SPOTIFY_AUTH_TOKEN_KEY: String = "spotify_auth_token_key"
        const val SPOTIFY_KEYSTORE_ALIAS: String = "spotify_keystore"
    }

    private val preferencesDataSourceComponent: PreferencesDataSourceComponent
    private val sharedPreferences: SharedPreferences
    private val gson: Gson
    private val keystore: KeyStore

    init {
        preferencesDataSourceComponent = DaggerPreferencesDataSourceComponent.builder()
                .contextModule(ContextModule(PlaylistTransferApplication.getInstance()))
                .build()
        gson = preferencesDataSourceComponent.getGson()
        sharedPreferences = preferencesDataSourceComponent.getSharedPreferences()
        keystore = preferencesDataSourceComponent.getKeystore()
    }

    override fun getSpotifyAuthToken(): AuthToken? {
        val encryptedString: String = sharedPreferences.getString(SPOTIFY_AUTH_TOKEN_KEY, "")
        if (encryptedString.isEmpty()) {
            return null
        }

        val jsonString: String = keystore.decrypt(encryptedString, SPOTIFY_KEYSTORE_ALIAS)
        return gson.fromJson(jsonString, AuthToken::class.java)
    }

    override fun saveSpotifyAuthToken(authToken: AuthToken) {
        val jsonString: String = gson.toJson(authToken)
        val encryptedString: String = keystore.encrypt(jsonString, SPOTIFY_KEYSTORE_ALIAS)
        sharedPreferences.edit().putString(SPOTIFY_AUTH_TOKEN_KEY, encryptedString).apply()
    }
}