package com.vincent.playlisttransferutility.ui.googlelogin

import android.content.Context
import android.telephony.TelephonyManager
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import svarzee.gps.gpsoauth.Gpsoauth
import javax.inject.Named

@Module
class GoogleLoginModule {

    @Provides
    fun provideGoogleLoginModel(context: Context,
                                gpsoauth: Gpsoauth,
                                telephonyManager: TelephonyManager): GoogleLoginModel {
        return GoogleLoginModel(context, gpsoauth, telephonyManager)
    }

    @Provides
    fun provideGoogleViewModel(resourceProvider: ResourceProvider,
                               schedulersProvider: SchedulersProvider,
                               model: GoogleLoginModel): GoogleLoginViewModel {
        return GoogleLoginViewModel(resourceProvider, schedulersProvider, model)
    }

    @Provides
    fun provideGpsOauth(@Named("GooglePlayMusic") okHttpClient: OkHttpClient): Gpsoauth {
        return Gpsoauth(okHttpClient)
    }

    @Provides
    fun provideTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
}