package com.vincent.playlisttransferutility.ui.playlistselection.di

import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModel
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