package com.vincent.playlisttransferutility.pages.playlistselection

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class, PlaylistSelectionModule::class])
@Singleton
interface PlaylistSelectionComponent {
    fun getRepository(): Repository
    fun getModel(): PlaylistSelectionModel
}