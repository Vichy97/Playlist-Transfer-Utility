package com.vincent.playlisttransferutility.utils.resources

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {

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