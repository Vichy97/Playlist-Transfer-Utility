package com.vincent.playlisttransferutility.pages.playlistselection

import com.vincent.playlisttransferutility.data.Repository
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PlaylistSelectionModel {

    private val repository: Repository
    private var transferFrom: MusicService = MusicService.GOOGLE_PLAY_MUSIC
    private var transferTo: MusicService = MusicService.SPOTIFY
    private val selectedPlaylistIds: Set<String> = HashSet()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val playlistSubject: BehaviorSubject<List<Playlist>> = BehaviorSubject.create()

    init {
        repository = DaggerPlaylistSelectionComponent.builder()
                .build()
                .getRepository()
    }

    fun onClear() {
        compositeDisposable.dispose()
    }

    fun getPlaylistEvents(): BehaviorSubject<List<Playlist>> {
        return playlistSubject
    }

    private fun getPlaylists(): Observable<List<Playlist>> {
        return when (transferFrom) {
            MusicService.SPOTIFY -> {
                getPlaylistsFromSpotify()
            }
            MusicService.APPLE_MUSIC -> {
                getPlaylistsFromAppleMusic()
            }
            MusicService.GOOGLE_PLAY_MUSIC -> {
                getPlaylistsFromGooglePlayMusic()
            }
        }
    }

    private fun getPlaylistsFromSpotify(): Observable<List<Playlist>> {
        return repository.getSpotifyPlaylists().map {
            val playlists: ArrayList<Playlist> = ArrayList()

            for (spotifyPlaylist: SpotifyPlaylist in it) {
                playlists.add(Playlist.fromSpotifyPlaylist(spotifyPlaylist))
            }

            return@map playlists
        }
    }

    private fun getPlaylistsFromGooglePlayMusic(): Observable<List<Playlist>> {
        return repository.getGooglePlayMusicPlaylists()
                .map {
            val playlists: ArrayList<Playlist> = ArrayList()

            for (playlist: com.github.felixgail.gplaymusic.model.Playlist in it) {
                playlists.add(Playlist.fromGooglePlayMusicPlaylist(playlist))
            }

            return@map playlists
        }
    }

    private fun getPlaylistsFromAppleMusic(): Observable<List<Playlist>> {
        //TODO: fill in when apple music is implemented
        return Observable.empty()
    }

    //Int represents transfer progress... maybe this should return something else though
    fun transfer(): Observable<Int> {
        //TODO: transfer selected playlists
        //for selected playlists ids:
        //    get playlist details for playlist id from transferFrom service
        //    create playlist in transferTo service using playlist details
        //        get tracks for playlist id in transferFrom service
        //        for each track in playlist
        //            search for track in transferTo service based on name and artist
        //            if first search result matches name and artist:
        //                add first search result to playlist
        //            else
        //                add to list of unavailable tracks to show user which tracks couldnt be found
        return Observable.empty()
    }

    fun setTransferFrom(musicService: MusicService) {
        if (transferFrom == musicService) {
            return
        }

        transferFrom = musicService
        compositeDisposable.add(
        getPlaylists().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    playlistSubject.onNext(it)
                }, {
                    playlistSubject.onError(it)
                }))
    }

    fun setTransferTo(musicService: MusicService) {
        if (transferTo == musicService) {
            return
        }

        transferTo = musicService
    }

    fun selectPlaylist(playlistId: String) {
        selectedPlaylistIds.plus(playlistId)
    }

    fun deselectPlaylist(playlistId: String) {
        selectedPlaylistIds.minus(playlistId)
    }
}