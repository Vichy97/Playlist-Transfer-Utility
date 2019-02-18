package com.vincent.playlisttransferutility.ui.googlelogin

import android.content.Context
import android.telephony.TelephonyManager
import com.vincent.playlisttransferutility.di.scopes.PerFragment
import com.vincent.playlisttransferutility.ui.base.BaseViewModelFactory
import com.vincent.playlisttransferutility.utils.CipherUtil
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import svarzee.gps.gpsoauth.Gpsoauth
import javax.inject.Named

@Module
abstract class GoogleLoginModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun provideGpsOauth(@Named("GooglePlayMusic") okHttpClient: OkHttpClient): Gpsoauth {
            return Gpsoauth(okHttpClient)
        }

        @JvmStatic
        @Provides
        @PerFragment
        fun provideTelephonyManager(context: Context): TelephonyManager {
            return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }

        @JvmStatic
        @Provides
        @PerFragment
        fun provideCipherUtil(): CipherUtil {
            return CipherUtil()
        }
    }

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: GoogleLoginViewModelFactory) : BaseViewModelFactory
}