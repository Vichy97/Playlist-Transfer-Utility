package com.vincent.playlisttransferutility.network.api

import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyPlaylistRequest
import com.vincent.playlisttransferutility.data.models.spotify.response.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApi {

    //region User
    @GET("/v1/me")
    fun getCurrentUser(): Single<SpotifyUser>
    //endregion User

    //region Playlists
    @POST("/v1/users/{userId}/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@Path("userId") userId: String,
                            @Path("playlistId") playlistId: String,
                            @Body trackUris: List<String>): Single<Response<ResponseBody>>

    @POST("/v1/users/{userId}/playlists")
    fun createPlaylist(@Path("userId") userId: String,
                       @Body playlist: SpotifyPlaylistRequest): Single<SpotifyPlaylist>

    @GET("/v1/me/playlists")
    fun getAllPlaylists(@Query("limit") limit: Int?,
                        @Query("offset") offset: Int?): Single<SpotifyPagingObject<SpotifyPlaylist>>

    @GET("/v1/users/{userId}/playlists/{playlistId}")
    fun getTracksForPlaylist(@Path("userId") userId: String,
                             @Path("playlistId") playlistId: String,
                             @Query("limit") limit: Int?): Single<SpotifyPlaylist>
    //endregion Playlists

    //region Search
    @GET("/v1/search")
    fun searchTracks(@Query("q", encoded = true) query: String,
                     @Query("type") type: String,
                     @Query("limit") limit: Int?): Single<SpotifyPagingObject<SpotifyTrack>>
    //endregion Search
}