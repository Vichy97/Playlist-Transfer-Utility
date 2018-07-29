package com.vincent.playlisttransferutility.pages.main

import dagger.Component
import javax.inject.Singleton

@Component(modules = [MainModule::class])
@Singleton
interface MainComponent {

    val mainModel: MainModel
}