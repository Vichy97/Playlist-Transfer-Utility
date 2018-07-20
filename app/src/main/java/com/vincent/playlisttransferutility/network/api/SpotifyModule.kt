package com.vincent.playlisttransferutility.network.api

import com.google.gson.Gson
import com.vincent.playlisttransferutility.network.OkHttpClientModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
class SpotifyModule {

    companion object {
        private const val SPOTIFY_API_BASE_URL: String = "https://api.spotify.com/v1"
    }

    @Provides
    @Singleton
    fun spotifyApi(retrofit: Retrofit): SpotifyApi {
        return retrofit.create(SpotifyApi::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient,
                 gsonConverterFactory: GsonConverterFactory,
                 rxJavaCallAdapterFactory: RxJavaCallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(SPOTIFY_API_BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    fun rxJavaCallAdapterFactory(): RxJavaCallAdapterFactory {
        return RxJavaCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return Gson()
    }
}