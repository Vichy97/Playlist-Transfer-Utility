package com.vincent.playlisttransferutility.pages.main

import com.vincent.playlisttransferutility.AppComponent
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
@MainScope
interface MainComponent {

    val mainModel: MainModel
}