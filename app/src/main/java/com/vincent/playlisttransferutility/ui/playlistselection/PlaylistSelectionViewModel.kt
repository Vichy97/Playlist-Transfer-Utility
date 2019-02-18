package com.vincent.playlisttransferutility.ui.playlistselection

import androidx.navigation.NavController
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import io.reactivex.Observable

class PlaylistSelectionViewModel(resourceProvider: ResourceProvider,
                                rxProvider: RxProvider,
                                navController: NavController,
                                private val interactor: PlaylistSelectionInteractor)
    : BaseViewModel(resourceProvider, rxProvider, navController) {

    override fun onCleared() {
        super.onCleared()

        interactor.onClear()
    }

    fun getPlaylistsEvents(): Observable<List<Playlist>> {
        return interactor.getPlaylistEvents()
    }

    fun onFromMusicServiceSelectionChanged(position: Int) {
        interactor.setTransferFrom(MusicService.values()[position])
    }

    fun onToMusicServiceSelectionChanged(position: Int) {
        interactor.setTransferTo(MusicService.values()[position])
    }

    fun onPlaylistSelectionChanged(playlistId: String, checked: Boolean) {
        //TODO: update viewstate
        if (checked) {
            interactor.selectPlaylist(playlistId)
        } else {
            interactor.deselectPlaylist(playlistId)
        }
    }

    fun onTransferClicked() {
        compositeDisposable.add(interactor.transfer()
                .subscribeOn(rxProvider.ioScheduler())
                .observeOn(rxProvider.uiScheduler())
                .subscribe())
        //TODO: only enabled if transferFrom and transferTo are not the same, and at least one playlist is selected
    }
}