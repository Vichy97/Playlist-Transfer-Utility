package com.vincent.playlisttransferutility.ui.playlistselection.di

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionFragment
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModel
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides

@Module
class PlaylistSelectionModule(private val fragment: PlaylistSelectionFragment) {

    @Provides
    @PlaylistSelectionScope
    fun providePlaylistSelectionModel(repository: Repository, spotifyApi: SpotifyApi): PlaylistSelectionModel {
        return PlaylistSelectionModel(repository, spotifyApi)
    }

    @Provides
    @PlaylistSelectionScope
    fun providePlaylistSelectionViewModelFactory(resourceProvider: ResourceProvider,
                                                 schedulersProvider: SchedulersProvider,
                                                 model: PlaylistSelectionModel): PlaylistSelectionViewModel.Factory {
        return PlaylistSelectionViewModel.Factory(resourceProvider, schedulersProvider, model)
    }

    @Provides
    @PlaylistSelectionScope
    fun providePlaylistSelectionViewModel(factory: PlaylistSelectionViewModel.Factory):
            PlaylistSelectionViewModel {
        return ViewModelProviders.of(fragment, factory)[PlaylistSelectionViewModel::class.java]
    }

    @Provides
    @PlaylistSelectionScope
    fun provideNavController(): NavController {
        return Navigation.findNavController(fragment.activity!!, R.id.nav_host)
    }
}