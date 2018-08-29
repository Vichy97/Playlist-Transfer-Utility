package com.vincent.playlisttransferutility.ui.playlistselection.di

import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionFragment
import dagger.Subcomponent

@Subcomponent(modules = [PlaylistSelectionModule::class])
@PlaylistSelectionScope
interface PlaylistSelectionComponent {

    fun inject(fragment: PlaylistSelectionFragment)
}