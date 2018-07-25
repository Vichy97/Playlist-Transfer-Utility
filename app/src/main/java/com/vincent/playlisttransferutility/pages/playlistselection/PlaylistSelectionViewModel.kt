package com.vincent.playlisttransferutility.pages.playlistselection

import android.arch.lifecycle.ViewModel
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.data.models.spotify.response.SpotifyPlaylist
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

//TODO: listen to spinner events
class PlaylistSelectionViewModel : ViewModel() {

    val model: PlaylistSelectionModel

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val playlistSubject: BehaviorSubject<List<Playlist>> = BehaviorSubject.create()

    val selectedPlaylists: Set<String> = HashSet()

    init {
        model = DaggerPlaylistSelectionComponent.builder().build().getModel()

        compositeDisposable.add(model.getPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    playlistSubject.onNext(it)
                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getPlaylistsEvent(): Observable<List<Playlist>> {
        return playlistSubject
    }

    fun onPlaylistSelectionChanged(playlistId: String, checked: Boolean) {
        //TODO: update viewstate
        if (checked) {
            selectedPlaylists.plus(playlistId)
        } else {
            selectedPlaylists.minus(playlistId)
        }
    }

    fun onTransferClicked() {
        //TODO: only enabled if transferFrom and transferTo are not the same, and at least one playlist is selected
    }
}