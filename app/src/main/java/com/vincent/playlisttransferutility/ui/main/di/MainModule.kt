package com.vincent.playlisttransferutility.ui.main.di

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.di.ViewModelKey
import com.vincent.playlisttransferutility.ui.main.MainModel
import com.vincent.playlisttransferutility.ui.main.MainViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MainModule {

    @Provides
    //@MainScope
    fun provideMainModel(spotifyRepository: SpotifyRepository): MainModel {
        return MainModel(spotifyRepository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    //@MainScope
    fun provideMainViewModel(resourceProvider: ResourceProvider,
                             schedulersProvider: SchedulersProvider,
                             model: MainModel): ViewModel {
        return MainViewModel(resourceProvider, schedulersProvider, model)
    }
}