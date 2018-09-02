package com.vincent.playlisttransferutility.network.googleplaymusic

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class GooglePlayNetworkModule {

    companion object {
        private const val BASE_URL: String = "https://www.googleapis.com/"
        private const val BASE_URL_AUTH: String = "https://android.clients.google.com/"
    }

    @Provides
    @Singleton
    fun provideGooglePlayMusicApi(@Named("GooglePlayMusic") retrofit: Retrofit): GooglePlayMusicApi {
        return retrofit.create(GooglePlayMusicApi::class.java)
    }

    @Provides
    @Named("GooglePlayMusic")
    @Singleton
    fun provideRetrofit(@Named("GooglePlayMusic") okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        rxJavaCallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(BASE_URL)
                .build()
    }

    @Provides
    @Named("GooglePlayMusic")
    @Singleton
    fun provideOkHttpClient(googlePlayMusicHeaderInterceptor: GooglePlayMusicHeaderInterceptor,
                            loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
                .addInterceptor(googlePlayMusicHeaderInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): GooglePlayMusicHeaderInterceptor {
        return GooglePlayMusicHeaderInterceptor()
    }
}