package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class ViewModelFactory(private val providerMap: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
        ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providerMap[modelClass]!!.get() as T
    }
}