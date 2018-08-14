package com.vincent.playlisttransferutility.pages.playlistselection.di

import com.vincent.playlisttransferutility.pages.playlistselection.PlaylistSelectionModel
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