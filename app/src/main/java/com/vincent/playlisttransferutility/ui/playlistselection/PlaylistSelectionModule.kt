package com.vincent.playlisttransferutility.ui.playlistselection

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides

@Module
class PlaylistSelectionModule {

    @Provides
    fun providePlaylistSelectionModel(spotifyRepository: SpotifyRepository,
                                      spotifyApi: SpotifyApi): PlaylistSelectionModel {
        return PlaylistSelectionModel(spotifyRepository, spotifyApi)
    }

    @Provides
    fun providePlaylistSelectionViewModel(resourceProvider: ResourceProvider,
                                          schedulersProvider: SchedulersProvider,
                                          model: PlaylistSelectionModel): PlaylistSelectionViewModel {
        return PlaylistSelectionViewModel(resourceProvider, schedulersProvider, model)
    }
}