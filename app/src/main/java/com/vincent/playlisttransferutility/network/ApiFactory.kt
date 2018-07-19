package com.vincent.playlisttransferutility.network

import com.vincent.playlisttransferutility.network.api.SpotifyAccountApi
import com.vincent.playlisttransferutility.network.api.SpotifyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    companion object {
        private const val SPOTIFY_API_BASE_URL: String = "https://api.spotify.com/v1"
        private const val SPOTIFY_ACCOUNTS_API_BASE_URL: String = "https://accounts.spotify.com"

        fun getSpotifyApi(): SpotifyApi {
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(SPOTIFY_API_BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit.create(SpotifyApi::class.java)
        }

        fun getSpotifyAuthorizeApi(): SpotifyAccountApi {
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(SPOTIFY_ACCOUNTS_API_BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit.create(SpotifyAccountApi::class.java)
        }

        //TODO: add logging interceptor
        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                    .addInterceptor(getHeaderInterceptor())
                    .build()
        }

        //TODO: add auth token
        private fun getHeaderInterceptor(): Interceptor {
            return Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept", "text/plain")
                        .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }
    }
}