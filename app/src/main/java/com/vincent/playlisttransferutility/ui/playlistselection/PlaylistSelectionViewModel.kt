package com.vincent.playlisttransferutility.ui.playlistselection

import android.util.Log
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Observable

class PlaylistSelectionViewModel(resourceProvider: ResourceProvider,
                                 schedulersProvider: SchedulersProvider,
                                 private val model: PlaylistSelectionModel)
    : BaseViewModel(resourceProvider, schedulersProvider) {

    init {
        Log.d("", "")
    }

    override fun onCleared() {
        super.onCleared()

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
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe())
        //TODO: only enabled if transferFrom and transferTo are not the same, and at least one playlist is selected
    }
}