package com.vincent.playlisttransferutility.network.spotify

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
class SpotifyNetworkModule {

    companion object {
        private const val BASE_URL: String = "https://api.spotify.com/v1/"
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(@Named("Spotify") retrofit: Retrofit): SpotifyApi {
        return retrofit.create(SpotifyApi::class.java)
    }

    @Provides
    @Named("Spotify")
    @Singleton
    fun provideRetrofit(@Named("Spotify") okHttpClient: OkHttpClient,
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
    @Named("Spotify")
    @Singleton
    fun provideOkHttpClient(spotifyHeaderInterceptor: SpotifyHeaderInterceptor,
                            loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
                .addInterceptor(spotifyHeaderInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): SpotifyHeaderInterceptor {
        return SpotifyHeaderInterceptor()
    }
}