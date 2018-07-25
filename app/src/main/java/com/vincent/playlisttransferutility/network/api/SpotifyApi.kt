package com.vincent.playlisttransferutility.network.api

import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPagingObject
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyTrack
import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyPlaylistRequest
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApi {

    //region Playlists
    @POST("/v1/users/{userId}/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@Path("userId") userId: String,
                            @Path("playlistId") playlistId: String,
                            @Body trackUris: List<String>): Single<Response<ResponseBody>>

    @POST("/v1/users/{userId}/playlists")
    fun createPlaylist(@Path("userId") userId: String,
                       @Body playlist: SpotifyPlaylistRequest): Single<SpotifyPagingObject<SpotifyPlaylist>>

    @GET("/v1/me/playlists")
    fun getAllPlaylists(@Query("limit") limit: Int?,
                        @Query("offset") offset: Int?): Single<SpotifyPagingObject<SpotifyPlaylist>>
    //endregion Playlists

    //region Search
    @GET("/search")
    fun searchTracks(@QueryMap queryMap: QueryMap): Single<SpotifyPagingObject<SpotifyTrack>>
    //endregion Search
}