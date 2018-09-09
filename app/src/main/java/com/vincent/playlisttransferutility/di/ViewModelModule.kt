package com.vincent.playlisttransferutility.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.playlisttransferutility.ui.base.ViewModelFactory
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginViewModel
import com.vincent.playlisttransferutility.ui.main.MainViewModel
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GoogleLoginViewModel::class)
    abstract fun bindGoogleLoginViewModel(mainViewModel: GoogleLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistSelectionViewModel::class)
    abstract fun bindPlaylistSelectionViewModel(playlistSelectionViewModel: PlaylistSelectionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}