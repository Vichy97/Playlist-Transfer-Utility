package com.vincent.playlisttransferutility.pages.playlistselection

import dagger.Component
import javax.inject.Singleton

@Component(modules = [PlaylistSelectionModule::class])
@PlaylistSelectionScope
interface PlaylistSelectionComponent {

    val playlistSelectionModel: PlaylistSelectionModel
}