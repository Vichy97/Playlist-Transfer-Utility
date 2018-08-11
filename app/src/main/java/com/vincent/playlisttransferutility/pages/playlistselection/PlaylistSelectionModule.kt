package com.vincent.playlisttransferutility.pages.playlistselection

import dagger.Module
import dagger.Provides

@Module
class PlaylistSelectionModule {

    @Provides
    @PlaylistSelectionScope
    fun providePlaylistSelectionModel(): PlaylistSelectionModel {
        return PlaylistSelectionModel()
    }
}