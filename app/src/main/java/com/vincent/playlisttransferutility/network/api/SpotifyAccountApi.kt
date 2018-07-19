package com.vincent.playlisttransferutility.network.api

import io.reactivex.Observable
import retrofit2.http.*

interface SpotifyAccountApi {

    @GET("/authorize")
    fun authorize(queryMap: QueryMap): Observable<String>

    @FormUrlEncoded
    @POST("/api/token")
    fun requestAccessAndRefreshTokens(@Field("grant_type") grantType: String,
                                      @Field("code") code: String,
                                      @Field("redirect_uri") redirectUri: String)
}