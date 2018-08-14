package com.vincent.playlisttransferutility.pages.main.di

import com.vincent.playlisttransferutility.pages.main.MainModel
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    val mainModel: MainModel
}