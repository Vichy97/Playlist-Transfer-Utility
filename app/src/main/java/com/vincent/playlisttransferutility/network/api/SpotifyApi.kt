package com.vincent.playlisttransferutility.network.api

import com.vincent.playlisttransferutility.data.models.spotify.PagingObject
import com.vincent.playlisttransferutility.data.models.spotify.Playlist
import com.vincent.playlisttransferutility.data.models.spotify.Track
import com.vincent.playlisttransferutility.data.models.spotify.request.PlaylistRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApi {

    //region Playlists
    @POST("/users/{userId}/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@Path("userId") userId: String,
                            @Path("playlistId") playlistId: String,
                            @Body trackUris: List<String>): Observable<Response<ResponseBody>>

    @POST("/users/{userId}/playlists")
    fun createPlaylist(@Path("userId") userId: String,
                       @Body playlist: PlaylistRequest): Observable<PagingObject<Playlist>>

    @GET("/v1/users/me/playlists")
    fun getAllPlaylists(): Observable<ArrayList<Playlist>>
    //endregion Playlists

    //region Search
    @GET("/search")
    fun searchTracks(@QueryMap queryMap: QueryMap): Observable<PagingObject<Track>>
    //endregion Search
}