package com.vincent.playlisttransferutility.ui.googlelogin.di

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModel
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides

@Module
class GoogleLoginModule(private val fragment: GoogleLoginDialogFragment) {

    @Provides
    @GoogleLoginScope
    fun provideGoogleLoginModel(repository: Repository): GoogleLoginModel {
        return GoogleLoginModel(repository)
    }

    @Provides
    @GoogleLoginScope
    fun provideGoogleLoginViewModelFactory(resourceProvider: ResourceProvider,
                                           schedulersProvider: SchedulersProvider,
                                           model: GoogleLoginModel):
            GoogleLoginViewModel.Factory {
        return GoogleLoginViewModel.Factory(resourceProvider, schedulersProvider, model)
    }

    @Provides
    @GoogleLoginScope
    fun provideGoogleViewModel(viewModelFactory: GoogleLoginViewModel.Factory): GoogleLoginViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory)[GoogleLoginViewModel::class.java]
    }

    @Provides
    @GoogleLoginScope
    fun provideNavController(): NavController {
        return Navigation.findNavController(fragment.activity!!, R.id.nav_host)
    }
}