package com.vincent.playlisttransferutility.pages.playlistselection

import dagger.Subcomponent

@Subcomponent(modules = [PlaylistSelectionModule::class])
@PlaylistSelectionScope
interface PlaylistSelectionComponent {

    val playlistSelectionModel: PlaylistSelectionModel
}