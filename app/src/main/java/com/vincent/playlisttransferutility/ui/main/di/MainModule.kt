package com.vincent.playlisttransferutility.ui.main.di

import com.vincent.playlisttransferutility.ui.main.MainModel
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