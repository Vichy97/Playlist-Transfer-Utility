package com.vincent.playlisttransferutility

import android.app.Application

class PlaylistTransferApplication : Application() {

    companion object {
        private lateinit var instance: PlaylistTransferApplication

        fun getInstance(): PlaylistTransferApplication {
            return instance
        }
    }

    init {
        instance = this
    }
}