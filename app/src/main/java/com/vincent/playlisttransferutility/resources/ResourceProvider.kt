package com.vincent.playlisttransferutility.resources

import android.content.Context
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import com.vincent.playlisttransferutility.ContextModule
import com.vincent.playlisttransferutility.PlaylistTransferApplication

class ResourceProvider {

    private val context: Context

    init {
        context = DaggerResourceProviderComponent.builder()
                .contextModule(ContextModule(PlaylistTransferApplication.getInstance()))
                .build()
                .getContext()
    }

    fun getString(@StringRes id: Int): String {
        return context.resources.getString(id)
    }

    fun getInt(@IntegerRes id: Int): Int {
        return context.resources.getInteger(id)
    }
}