package com.vincent.playlisttransferutility.ui.playlistselection

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class PlaylistSelectionModule {

    @Provides
    fun providePlaylistSelectionModel(spotifyRepository: SpotifyRepository,
                                      spotifyApi: SpotifyApi): PlaylistSelectionModel {
        return PlaylistSelectionModel(spotifyRepository, spotifyApi)
    }

    @Provides
    fun providePlaylistSelectionViewModel(resourceProvider: ResourceProvider,
                                          rxProvider: RxProvider,
                                          model: PlaylistSelectionModel): PlaylistSelectionViewModel {
        return PlaylistSelectionViewModel(resourceProvider, rxProvider, model)
    }
}