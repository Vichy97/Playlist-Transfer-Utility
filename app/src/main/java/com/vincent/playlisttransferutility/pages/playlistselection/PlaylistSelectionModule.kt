package com.vincent.playlisttransferutility.pages.playlistselection

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlaylistSelectionModule {

    @Provides
    @Singleton
    fun provideModel(): PlaylistSelectionModel {
        return PlaylistSelectionModel()
    }
}