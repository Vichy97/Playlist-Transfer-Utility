package com.vincent.playlisttransferutility.ui.main.di

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.ui.main.MainFragment
import com.vincent.playlisttransferutility.ui.main.MainModel
import com.vincent.playlisttransferutility.ui.main.MainViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val fragment: MainFragment) {

    @Provides
    @MainScope
    fun provideMainModel(repository: Repository): MainModel {
        return MainModel(repository)
    }

    @Provides
    @MainScope
    fun provideMainViewModelFactory(resourceProvider: ResourceProvider,
                                    schedulersProvider: SchedulersProvider,
                                    model: MainModel): MainViewModel.Factory {
        return MainViewModel.Factory(resourceProvider, schedulersProvider, model)
    }

    @Provides
    @MainScope
    fun provideMainViewModel(factory: MainViewModel.Factory): MainViewModel {
        return ViewModelProviders.of(fragment, factory)[MainViewModel::class.java]
    }

    @Provides
    @MainScope
    fun provideNavController(): NavController {
        return Navigation.findNavController(fragment.activity!!, R.id.nav_host)
    }
}