package com.vincent.playlisttransferutility.ui.playlistselection.di

import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModel
import dagger.Subcomponent

@Subcomponent(modules = [PlaylistSelectionModule::class])
@PlaylistSelectionScope
interface PlaylistSelectionComponent {

    val playlistSelectionModel: PlaylistSelectionModel
}