package com.vincent.playlisttransferutility.pages.playlistselection

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.pages.playlistselection.di.PlaylistSelectionModule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PlaylistSelectionViewModel : ViewModel() {

    private val model: PlaylistSelectionModel

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        model = AppComponent.instance
                .newPlaylistSelectionComponent(PlaylistSelectionModule())
                .playlistSelectionModel
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
        model.onClear()
    }

    fun getPlaylistsEvents(): Observable<List<Playlist>> {
        return model.getPlaylistEvents()
    }

    fun onFromMusicServiceSelectionChanged(position: Int) {
        model.setTransferFrom(MusicService.values()[position])
    }

    fun onToMusicServiceSelectionChanged(position: Int) {
        model.setTransferTo(MusicService.values()[position])
    }

    fun onPlaylistSelectionChanged(playlistId: String, checked: Boolean) {
        //TODO: update viewstate
        if (checked) {
            model.selectPlaylist(playlistId)
        } else {
            model.deselectPlaylist(playlistId)
        }
    }

    fun onTransferClicked() {
        compositeDisposable.add(model.transfer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        //TODO: only enabled if transferFrom and transferTo are not the same, and at least one playlist is selected
    }
}