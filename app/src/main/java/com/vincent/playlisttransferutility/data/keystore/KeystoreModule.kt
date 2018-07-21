package com.vincent.playlisttransferutility.data.keystore

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class KeystoreModule {

    @Provides
    @Singleton
    fun provideKeystore(): KeyStore {
        return KeyStore()
    }
}