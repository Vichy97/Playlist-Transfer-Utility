package com.vincent.playlisttransferutility.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private var accessToken: String? = null

       init {
        accessToken = ""
    }

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Accept", "text/plain")
                .method(original.method(), original.body())

        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + accessToken!!)
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}