package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory : ViewModelProvider.Factory {

    abstract fun getViewModelClass(): Class<out BaseViewModel>
}