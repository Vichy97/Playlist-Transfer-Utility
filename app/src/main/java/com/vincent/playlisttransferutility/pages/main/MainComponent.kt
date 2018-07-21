package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.data.Repository
import dagger.Component

@Component
interface MainComponent {

    fun getRepository(): Repository
    fun getMainModel(): MainModel
}