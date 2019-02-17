package com.vincent.playlisttransferutility.utils

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(private val context: Context) {

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