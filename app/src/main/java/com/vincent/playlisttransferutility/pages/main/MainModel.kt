package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.data.Repository

class MainModel {

    private val repository: Repository

    init {
        repository = DaggerMainComponent.builder()
                .build()
                .getRepository()
    }

}