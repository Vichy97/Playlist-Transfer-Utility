package com.vincent.playlisttransferutility.pages.googlelogin.di

import com.vincent.playlisttransferutility.pages.googlelogin.GoogleLoginModel
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