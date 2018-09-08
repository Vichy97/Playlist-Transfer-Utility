package com.vincent.playlisttransferutility.ui.base

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(providerMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }
}