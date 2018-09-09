package com.vincent.playlisttransferutility.ui.main

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainModel(spotifyRepository: SpotifyRepository): MainModel {
        return MainModel(spotifyRepository)
    }

    @Provides
    fun provideMainViewModel(resourceProvider: ResourceProvider,
                             schedulersProvider: SchedulersProvider,
                             model: MainModel): MainViewModel {
        return MainViewModel(resourceProvider, schedulersProvider, model)
    }
}