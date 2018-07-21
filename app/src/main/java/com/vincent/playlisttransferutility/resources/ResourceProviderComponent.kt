package com.vincent.playlisttransferutility.resources

import android.content.Context
import com.vincent.playlisttransferutility.ContextModule
import dagger.Component

@Component(modules = [ContextModule::class])
interface ResourceProviderComponent {

    fun getContext(): Context
}