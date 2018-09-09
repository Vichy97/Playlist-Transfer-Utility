package com.vincent.playlisttransferutility.di

import androidx.lifecycle.ViewModel
import com.vincent.playlisttransferutility.ui.base.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap);
    }
}