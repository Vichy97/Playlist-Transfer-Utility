package com.vincent.playlisttransferutility.utils.resources

import android.content.Context
import com.vincent.playlisttransferutility.ContextModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class])
@Singleton
interface ResourceProviderComponent {

    fun getContext(): Context
}