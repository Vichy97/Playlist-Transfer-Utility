package com.vincent.playlisttransferutility.ui.googlelogin.di

import android.content.Context
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.repository.Repository
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayMusicApi
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModel
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import svarzee.gps.gpsoauth.Gpsoauth
import javax.inject.Named

@Module
class GoogleLoginModule(private val fragment: GoogleLoginDialogFragment) {

    @Provides
    @GoogleLoginScope
    fun provideGoogleLoginModel(context: Context,
                                repository: Repository,
                                gpsoauth: Gpsoauth,
                                telephonyManager: TelephonyManager,
                                googlePlayMusicApi: GooglePlayMusicApi): GoogleLoginModel {
        return GoogleLoginModel(context, repository, gpsoauth, telephonyManager, googlePlayMusicApi)
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

    @Provides
    @GoogleLoginScope
    fun provideGpsOauth(@Named("GooglePlayMusic") okHttpClient: OkHttpClient): Gpsoauth {
        return Gpsoauth(okHttpClient)
    }

    @Provides
    @GoogleLoginScope
    fun provideTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
}