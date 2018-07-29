package com.vincent.playlisttransferutility.pages.playlistselection

import dagger.Component
import javax.inject.Singleton

@Component(modules = [PlaylistSelectionModule::class])
@Singleton
interface PlaylistSelectionComponent {

    val playlistSelectionModel: PlaylistSelectionModel
}