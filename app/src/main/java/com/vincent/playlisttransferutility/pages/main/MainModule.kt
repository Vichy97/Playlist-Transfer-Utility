package com.vincent.playlisttransferutility.pages.main

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