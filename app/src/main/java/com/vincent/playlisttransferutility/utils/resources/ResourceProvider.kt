package com.vincent.playlisttransferutility.utils.resources

import android.content.Context
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import com.vincent.playlisttransferutility.AppComponent

class ResourceProvider {

    private val context: Context = AppComponent.instance.context

    fun getString(@StringRes id: Int): String {
        return context.resources.getString(id)
    }

    //FIXME: this doesnt work for some reason...
    fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return context.resources.getString(id, formatArgs)
    }

    fun getInt(@IntegerRes id: Int): Int {
        return context.resources.getInteger(id)
    }
}