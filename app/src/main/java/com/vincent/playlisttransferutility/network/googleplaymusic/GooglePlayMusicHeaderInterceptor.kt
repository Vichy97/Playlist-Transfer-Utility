package com.vincent.playlisttransferutility.network.googleplaymusic

import okhttp3.Interceptor
import okhttp3.Response

class GooglePlayMusicHeaderInterceptor : Interceptor {

    private var accessToken: String? = null

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .method(original.method(), original.body())

        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", "GoogleLogin auth=" + accessToken!!)
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}