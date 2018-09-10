package com.vincent.playlisttransferutility.ui.main

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainModule {

    @Provides
    fun provideMainModel(spotifyRepository: SpotifyRepository): MainModel {
        return MainModel(spotifyRepository)
    }

    @Provides
    fun provideMainViewModel(resourceProvider: ResourceProvider,
                             schedulersProvider: SchedulersProvider,
                             compositeDisposable: CompositeDisposable,
                             model: MainModel): MainViewModel {
        return MainViewModel(resourceProvider, schedulersProvider, compositeDisposable, model)
    }
}