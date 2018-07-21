package com.vincent.playlisttransferutility.pages.main

import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainModel(): MainModel {
        return MainModel()
    }
    
    @Provides
    fun provideMainViewModel(): MainViewModel {
        return MainViewModel()
    }
}