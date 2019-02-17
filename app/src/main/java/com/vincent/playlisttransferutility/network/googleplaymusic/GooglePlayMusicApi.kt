package com.vincent.playlisttransferutility.network.googleplaymusic

import io.reactivex.Single
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

interface GooglePlayMusicApi {

    @FormUrlEncoded
    @POST("/auth")
    fun authenticate(@Url url: String,
                     @Field("EncryptedPasswd") password: String,
                     @Field("device_country") deviceCountry: String,
                     @Field("accountType") accountType: String,
                     @Field("androidId") androidId: String,
                     @Field("Email") email: String,
                     @Field("lang") language: String,
                     @Field("add_account") addAccount: Int,
                     @Field("service") service: String,
                     @Field("operatorCountry") operatorCountry: String,
                     @Field("source") source: String,
                     @Field("sdk_version") sdkVersion: Int,
                     @Field("has_permission") hasPermission: Int): Single<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("/auth")
    fun oauth(@Url url: String,
              @Field("EncryptedPasswd") password: String,
              @Field("app") app: String,
              @Field("device_country") deviceCountry: String,
              @Field("accountType") accountType: String,
              @Field("androidId") androidId: String,
              @Field("Email") email: String,
              @Field("lang") language: String,
              @Field("service") service: String,
              @Field("operatorCountry") operatorCountry: String,
              @Field("source") source: String,
              @Field("sdk_version") sdkVersion: Int,
              @Field("has_permission") hasPermission: Int): Single<Response<ResponseBody>>

    @POST("/sj/v2.4/plentriesbatch")
    fun addTracksToPlaylist(@Query("alt") str: String,
                            @Body request: Request): Single<Response<ResponseBody>>

    @POST("/sj/v2.4/playlistbatch")
    fun createPlaylist(@Query("alt") str: String,
                       @Body request: Request): Single<Response<ResponseBody>>

    @POST("/sj/v2.4/plentryfeed")
    fun getAllTracks(@Body hashMap: HashMap<String, Any>): Single<Response<ResponseBody>>

    @POST("/sj/v2.4/playlistfeed")
    fun getPlaylists(@Header("Content-Type") str: String): Single<Response<ResponseBody>>

    @GET("/sj/v2.4/query")
    fun searchTrack(@Query("max-results") i: Int,
                    @Query("q") str: String,
                    @Query("ct") str2: String): Single<Response<ResponseBody>>
}