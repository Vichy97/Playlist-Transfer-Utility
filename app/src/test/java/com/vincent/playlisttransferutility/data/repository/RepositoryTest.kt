package com.vincent.playlisttransferutility.data.repository

import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyPlaylistRequest
import com.vincent.playlisttransferutility.data.models.spotify.response.*
import com.vincent.playlisttransferutility.data.sources.DataSource
import com.vincent.playlisttransferutility.network.spotify.SpotifyApi
import com.vincent.playlisttransferutility.network.spotify.SpotifyHeaderInterceptor
import io.reactivex.Single
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.validateMockitoUsage
import retrofit2.Response

open class RepositoryTest {

    @Mock
    private lateinit var dataSource: DataSource
    @Mock
    private lateinit var spotifyApi: SpotifyApi
    @Mock
    private lateinit var spotifyHeaderInterceptor: SpotifyHeaderInterceptor

    private lateinit var testRepository: Repository

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        testRepository = Repository(dataSource, spotifyApi, spotifyHeaderInterceptor)
    }

    @After
    fun validate() {
        validateMockitoUsage()
    }

    @Test
    fun `when auth token is set, header access token should be set`() {
        val authToken = getAuthToken()
        givenAuthTokenSet(authToken)

        Mockito.verify(spotifyHeaderInterceptor).setAccessToken(authToken.accessToken)
    }

    @Test
    fun `get auth token should return previously set token`() {
        val authToken = getAuthToken()
        givenAuthTokenSet(authToken)

        testRepository.getSpotifyAuthToken()
                .test()
                .assertResult(authToken)
    }

    @Test
    fun `get all playlists should return mutable list from api`() {
        val playlistPagingObject: SpotifyPagingObject<SpotifyPlaylist> = getTestPlaylistPagingObject()
        `when`(spotifyApi.getAllPlaylists(any(), any())).thenReturn(Single.just(playlistPagingObject))

        testRepository.getSpotifyPlaylists()
                .test()
                .assertValue(playlistPagingObject.items)

        verify(spotifyApi).getAllPlaylists(null, null)
    }

    @Test
    fun `creating playlist without setting auth token should return error`() {
        testRepository.createSpotifyPlaylist("playlist name")
                .test()
                .assertFailure(Exception::class.java)
    }

    @Test
    fun `creating playlist should create playlist on api`() {
        givenAuthTokenSet(getAuthToken())
        val playlistRequest = SpotifyPlaylistRequest("playlist name", null, null, null)
        `when`(spotifyApi.createPlaylist(any(), any())).thenReturn(Single.just(getSpotifyPlaylist()))

        testRepository.createSpotifyPlaylist("playlist name")
                .test()
                .assertResult()

        verify(spotifyApi).createPlaylist(getUser().id, playlistRequest)
    }

    @Test
    fun `adding tracks to playlist without setting auth token should return error`() {
        testRepository.addTracksToSpotifyPlaylist("playlistId", getTracks())
                .test()
                .assertFailure(Exception::class.java)
    }

    @Test
    fun `adding tracks to playlist should add tracks to playlist on api`() {
        givenAuthTokenSet(getAuthToken())
        val playlistId = "playlistId"
        val tracks = getTracks()
        `when`(spotifyApi.addTracksToPlaylist(any(), any(), any())).thenReturn(Single.just(getSpotifyPlaylist()))

        testRepository.addTracksToSpotifyPlaylist(playlistId, tracks)
                .test()
                .assertResult()
    }

    @Test
    fun `getting tracks without setting auth token should return error`() {
        testRepository.getSpotifyTracks("playlistId")
                .test()
                .assertFailure(Exception::class.java)
    }

    @Test
    fun `getting tracks should get tracks from api`() {
        givenAuthTokenSet(getAuthToken())
        `when`(spotifyApi.getTracksForPlaylist(any(), any(), any())).thenReturn(Single.just(getSpotifyPlaylist()))

        testRepository.getSpotifyTracks("playlistId")
                .test()
                .assertResult(getTracks())
    }

    private fun givenAuthTokenSet(authToken: AuthToken) {
        `when`(spotifyApi.getCurrentUser()).thenReturn(Single.just(getUser()))

        testRepository.setSpotifyAuthToken(authToken)
                .test()
                .assertResult()
    }

    private fun getAuthToken(): AuthToken {
        return AuthToken("access token", MusicService.SPOTIFY, -1)
    }

    private fun getUser(): SpotifyUser {
        return SpotifyUser("id", "uri", "displayName", listOf())
    }

    private fun getTestPlaylistPagingObject(): SpotifyPagingObject<SpotifyPlaylist> {
        val tracksPagingObject: SpotifyPagingObject<SpotifyPlaylistTrack> = getTestPlaylistTracksPagingObject()
        val playlists: List<SpotifyPlaylist> = listOf(
                SpotifyPlaylist("id", false, listOf(), "name", true, tracksPagingObject),
                SpotifyPlaylist("id2", false, listOf(), "name2", true, tracksPagingObject)
        )

        return SpotifyPagingObject(playlists, 2, 0, null)
    }

    private fun getTracks(): List<SpotifyTrack> {
        return listOf(
                SpotifyTrack("id", "uri", "name", listOf()),
                SpotifyTrack("id2", "uri2", "name2", listOf())
        )
    }

    private fun getTestPlaylistTracksPagingObject(): SpotifyPagingObject<SpotifyPlaylistTrack> {
        val tracks = getTracks()
        val playlistTracks: List<SpotifyPlaylistTrack> = listOf(
                SpotifyPlaylistTrack(tracks[0]),
                SpotifyPlaylistTrack(tracks[1])
        )
        return SpotifyPagingObject(playlistTracks, 2, 0, null)
    }

    private fun getSpotifyPlaylist(): SpotifyPlaylist {
        return SpotifyPlaylist("id", false, listOf(), "name", false, getTestPlaylistTracksPagingObject())
    }
}