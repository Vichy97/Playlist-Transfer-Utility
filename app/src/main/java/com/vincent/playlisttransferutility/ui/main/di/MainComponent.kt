package com.vincent.playlisttransferutility.ui.main.di

import com.vincent.playlisttransferutility.ui.main.MainModel
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    val mainModel: MainModel
}