package com.vincent.playlisttransferutility.ui.playlistselection

import com.vincent.playlisttransferutility.data.spotify.SpotifyRepository
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
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
                                          schedulersProvider: SchedulersProvider,
                                          compositeDisposable: CompositeDisposable,
                                          model: PlaylistSelectionModel): PlaylistSelectionViewModel {
        return PlaylistSelectionViewModel(resourceProvider, schedulersProvider, compositeDisposable, model)
    }
}