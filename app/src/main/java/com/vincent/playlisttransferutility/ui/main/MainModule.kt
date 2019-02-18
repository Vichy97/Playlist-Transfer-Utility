package com.vincent.playlisttransferutility.ui.main

import com.vincent.playlisttransferutility.ui.base.BaseViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: MainViewModelFactory): BaseViewModelFactory
}