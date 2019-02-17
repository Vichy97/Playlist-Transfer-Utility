package com.vincent.playlisttransferutility.ui.googlelogin

import android.content.Context
import android.telephony.TelephonyManager
import com.vincent.playlisttransferutility.network.googleplaymusic.GooglePlayMusicApi
import com.vincent.playlisttransferutility.utils.CipherUtil
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import svarzee.gps.gpsoauth.Gpsoauth
import javax.inject.Named

@Module
class GoogleLoginModule {

    @Provides
    fun provideGoogleLoginModel(context: Context,
                                googlePlayMusicApi: GooglePlayMusicApi,
                                gpsoauth: Gpsoauth,
                                cipherUtil: CipherUtil,
                                telephonyManager: TelephonyManager): GoogleLoginModel {
        return GoogleLoginModel(context, googlePlayMusicApi, telephonyManager, cipherUtil)
    }

    @Provides
    fun provideGoogleViewModel(resourceProvider: ResourceProvider,
                               rxProvider: RxProvider,
                               model: GoogleLoginModel): GoogleLoginViewModel {
        return GoogleLoginViewModel(resourceProvider, rxProvider, model)
    }

    @Provides
    fun provideGpsOauth(@Named("GooglePlayMusic") okHttpClient: OkHttpClient): Gpsoauth {
        return Gpsoauth(okHttpClient)
    }

    @Provides
    fun provideTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    @Provides
    fun provideCipherUtil(): CipherUtil {
        return CipherUtil()
    }
}