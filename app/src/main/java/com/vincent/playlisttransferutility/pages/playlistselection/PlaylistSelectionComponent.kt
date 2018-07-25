package com.vincent.playlisttransferutility.pages.playlistselection

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class, PlaylistSelectionModule::class, ResourceProviderModule::class])
@Singleton
interface PlaylistSelectionComponent {
    fun getRepository(): Repository
    fun getModel(): PlaylistSelectionModel
    fun getResourceProvider(): ResourceProvider
}