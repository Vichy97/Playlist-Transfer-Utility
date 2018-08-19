package com.vincent.playlisttransferutility.ui.googlelogin.di

import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModel
import dagger.Module
import dagger.Provides

@Module
class GoogleLoginModule {

    @Provides
    @GoogleLoginScope
    fun provideGoogleLoginModel(): GoogleLoginModel {
        return GoogleLoginModel()
    }
}