package com.vincent.playlisttransferutility.pages.playlistselection.di

import com.vincent.playlisttransferutility.pages.playlistselection.PlaylistSelectionModel
import dagger.Subcomponent

@Subcomponent(modules = [PlaylistSelectionModule::class])
@PlaylistSelectionScope
interface PlaylistSelectionComponent {

    val playlistSelectionModel: PlaylistSelectionModel
}