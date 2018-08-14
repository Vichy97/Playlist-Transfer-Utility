package com.vincent.playlisttransferutility.pages.main.di

import com.vincent.playlisttransferutility.pages.main.MainModel
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @MainScope
    fun provideMainModel(): MainModel {
        return MainModel()
    }
}