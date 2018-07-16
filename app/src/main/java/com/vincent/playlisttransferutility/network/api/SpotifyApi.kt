package com.vincent.playlisttransferutility.network.api

import android.provider.MediaStore
import com.vincent.playlisttransferutility.data_objects.spotify.PagingObject
import com.vincent.playlisttransferutility.data_objects.spotify.Playlist
import com.vincent.playlisttransferutility.data_objects.spotify.Track
import com.vincent.playlisttransferutility.data_objects.spotify.request.PlaylistRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface SpotifyApi {

    //region Playlists
    @POST("/users/{userId}/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@Path("userId") userId: String,
                            @Path("playlistId") playlistId: String,
                            trackUris: List<String>): Observable<Response<ResponseBody>>

    @POST("/users/{userId}/playlists")
    fun createPlaylist(@Path("userId") userId: String,
                       playlist: PlaylistRequest): Observable<PagingObject<Playlist>>

    @GET("/users/playlists")
    fun getAllPlaylists(limit: Int?): Observable<List<MediaStore.Audio.Playlists>>
    //endregion Playlists

    //region Search
    @GET("/search")
    fun searchTracks(@QueryMap queryMap: QueryMap): PagingObject<Track>
    //endregion Search
}