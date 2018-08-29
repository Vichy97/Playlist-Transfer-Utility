package com.vincent.playlisttransferutility.ui.main.di

import com.vincent.playlisttransferutility.ui.main.MainFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    fun inject(mainFragment: MainFragment)
}