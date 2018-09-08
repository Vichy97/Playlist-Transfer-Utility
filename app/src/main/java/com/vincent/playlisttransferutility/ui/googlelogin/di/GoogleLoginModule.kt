package com.vincent.playlisttransferutility.ui.googlelogin.di

import android.content.Context
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.di.ViewModelKey
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModel
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import svarzee.gps.gpsoauth.Gpsoauth
import javax.inject.Named

@Module
class GoogleLoginModule {

    @Provides
    //@GoogleLoginScope
    fun provideGoogleLoginModel(context: Context,
                                gpsoauth: Gpsoauth,
                                telephonyManager: TelephonyManager): GoogleLoginModel {
        return GoogleLoginModel(context, gpsoauth, telephonyManager)
    }

    @Provides
    @IntoMap
    @ViewModelKey(GoogleLoginViewModel::class)
   // @GoogleLoginScope
    fun provideGoogleViewModel(resourceProvider: ResourceProvider,
                               schedulersProvider: SchedulersProvider,
                               model: GoogleLoginModel): ViewModel {
        return GoogleLoginViewModel(resourceProvider, schedulersProvider, model)
    }

    @Provides
    //@GoogleLoginScope
    fun provideGpsOauth(@Named("GooglePlayMusic") okHttpClient: OkHttpClient): Gpsoauth {
        return Gpsoauth(okHttpClient)
    }

    @Provides
    //@GoogleLoginScope
    fun provideTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
}