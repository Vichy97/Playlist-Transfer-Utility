package com.vincent.playlisttransferutility.network

import com.vincent.playlisttransferutility.ContextModule
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [(ContextModule::class)])
class OkHttpClientModule {

    @Provides
    @Singleton
    fun okHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
                .addInterceptor(headerInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun headerInterceptor(): Interceptor {
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