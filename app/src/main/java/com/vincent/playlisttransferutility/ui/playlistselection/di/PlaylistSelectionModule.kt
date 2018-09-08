package com.vincent.playlisttransferutility.ui.playlistselection.di

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.di.ViewModelKey
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModel
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PlaylistSelectionModule {

    @Provides
    //@PlaylistSelectionScope
    fun providePlaylistSelectionModel(spotifyRepository: SpotifyRepository,
                                      spotifyApi: SpotifyApi): PlaylistSelectionModel {
        return PlaylistSelectionModel(spotifyRepository, spotifyApi)
    }

    @Provides
    @IntoMap
    @ViewModelKey(PlaylistSelectionViewModel::class)
    //@PlaylistSelectionScope
    fun providePlaylistSelectionViewModel(resourceProvider: ResourceProvider,
                                          schedulersProvider: SchedulersProvider,
                                          model: PlaylistSelectionModel): ViewModel {
        return PlaylistSelectionViewModel(resourceProvider, schedulersProvider, model)
    }
}