package com.vincent.playlisttransferutility.pages.main

import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    val mainModel: MainModel
}