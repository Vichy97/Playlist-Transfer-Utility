package com.vincent.playlisttransferutility.network.api

import com.google.gson.Gson
import com.vincent.playlisttransferutility.data.GsonModule
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class, GsonModule::class])
class SpotifyModule {

    companion object {
        private const val SPOTIFY_API_BASE_URL: String = "https://api.spotify.com/v1/"
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(retrofit: Retrofit): SpotifyApi {
        return retrofit.create(SpotifyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                 gsonConverterFactory: GsonConverterFactory,
                 rxJavaCallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(SPOTIFY_API_BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}