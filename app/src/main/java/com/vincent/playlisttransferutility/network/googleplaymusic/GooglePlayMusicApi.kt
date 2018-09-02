package com.vincent.playlisttransferutility.network.googleplaymusic

import io.reactivex.Single
import retrofit2.http.*
import java.util.HashMap

interface GooglePlayMusicApi {

//    @POST("/sj/v2.4/plentriesbatch")
//    fun addTracksToPlaylist(@Query("alt") str: String,
//                                     @Body request: Request): Single<GPMAddTracksToPlaylistResult>
//
//    @FormUrlEncoded
//    @POST("/auth")
//    fun auth1(@Field("EncryptedPasswd") password: String,
//                       @Field("device_country") deviceCountry: String,
//                       @Field("accountType") accountType: String,
//                       @Field("androidId") androidId: Int,
//                       @Field("Email") email: String,
//                       @Field("lang") language: String,
//                       @Field("add_account") addAccount: Int,
//                       @Field("service") service: String,
//                       @Field("operatorCountry") operatorCountry: String,
//                       @Field("source") source: String,
//                       @Field("sdk_version") sdkVersion: Int,
//                       @Field("has_permission") hasPermission: Int): Single<GPMAuth1Result>
//
//    @FormUrlEncoded
//    @POST("/auth")
//    fun auth2(@Field("EncryptedPasswd") password: String,
//                       @Field("app") app: String,
//                       @Field("device_country") deviceCountry: String,
//                       @Field("accountType") accountType: String,
//                       @Field("androidId") androidId: Int,
//                       @Field("Email") email: String,
//                       @Field("lang") language: String,
//                       @Field("service") service: String,
//                       @Field("operatorCountry") operatorCountry: String,
//                       @Field("source") source: String,
//                       @Field("sdk_version") sdkVersion: Int,
//                       @Field("has_permission") hasPermission: Int): Single<GPMAuth2Result>
//
//    @POST("/sj/v2.4/playlistbatch")
//    fun createPlaylist(@Query("alt") str: String,
//                                @Body request: GPMCreatePlaylist.Request): Single<GPMCreatePlaylistResult>
//
//    @POST("/sj/v2.4/plentryfeed")
//    fun getAllTracks(@Body hashMap: HashMap<String, Any>): Single<GPMGetAllTracksResult>
//
//    @POST("/sj/v2.4/playlistfeed")
//    fun getPlaylists(@Header("Content-Type") str: String): Single<GPMGetPlaylistResult>
//
//    @GET("/sj/v2.4/query")
//    fun searchTrack(@Query("max-results") i: Int,
//                             @Query("q") str: String,
//                             @Query("ct") str2: String): Single<GPMSearchTrackResult>
}