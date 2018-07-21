package com.vincent.playlisttransferutility.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class OkHttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor,
                     loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
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

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

}