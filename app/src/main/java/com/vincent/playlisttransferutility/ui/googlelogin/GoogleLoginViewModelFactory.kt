package com.vincent.playlisttransferutility.ui.googlelogin

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.vincent.playlisttransferutility.di.scopes.PerFragment
import com.vincent.playlisttransferutility.ui.base.BaseViewModel
import com.vincent.playlisttransferutility.ui.base.BaseViewModelFactory
import com.vincent.playlisttransferutility.utils.ResourceProvider
import com.vincent.playlisttransferutility.utils.RxProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@PerFragment
class GoogleLoginViewModelFactory @Inject constructor(private val rxProvider: RxProvider,
                                                      private val resourceProvider: ResourceProvider,
                                                      private val navController: NavController,
                                                      private val interactor: GoogleLoginInteractor)
    : BaseViewModelFactory() {

    override fun getViewModelClass(): Class<out BaseViewModel> {
        return GoogleLoginViewModel::class.java
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(GoogleLoginViewModel::class.java)) {
            throw IllegalArgumentException("Unexpected ViewModel Class")
        }

        return GoogleLoginViewModel(resourceProvider, rxProvider, navController, interactor) as T
    }
}
