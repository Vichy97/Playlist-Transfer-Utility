package com.vincent.playlisttransferutility.ui.playlistselection

import com.vincent.playlisttransferutility.ui.base.BaseViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class PlaylistSelectionModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: PlaylistSelectionViewModelFactory): BaseViewModelFactory
}