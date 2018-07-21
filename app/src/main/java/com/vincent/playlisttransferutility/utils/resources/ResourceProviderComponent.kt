package com.vincent.playlisttransferutility.utils.resources

import android.content.Context
import com.vincent.playlisttransferutility.ContextModule
import dagger.Component

@Component(modules = [ContextModule::class])
interface ResourceProviderComponent {

    fun getContext(): Context
}