package com.vincent.playlisttransferutility.ui.playlistselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.vincent.playlisttransferutility.di.scopes.PerFragment
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.ui.base.BaseViewModelFactory
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@PerFragment
class PlaylistSelectionViewModelFactory @Inject constructor(private val rxProvider: RxProvider,
                                                            private val resourceProvider: ResourceProvider,
                                                            private val navController: NavController,
                                                            private val interactor: PlaylistSelectionInteractor)
    : BaseViewModelFactory() {

    override fun getViewModelClass(): Class<out BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(PlaylistSelectionViewModel::class.java)) {
            throw IllegalArgumentException("Unexpected ViewModel Class")
        }

        return PlaylistSelectionViewModel(resourceProvider, rxProvider, navController, interactor) as T
    }
}