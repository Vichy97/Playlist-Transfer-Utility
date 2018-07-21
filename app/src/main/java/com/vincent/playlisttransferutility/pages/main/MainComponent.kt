package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.RepositoryModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.resources.ResourceProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ResourceProviderModule::class, RepositoryModule::class, MainModule::class])
@Singleton
interface MainComponent {

    fun getMainModel(): MainModel
    fun getMainViewModel(): MainViewModel
    fun getRepository(): Repository
    fun getResourceProvider(): ResourceProvider
}