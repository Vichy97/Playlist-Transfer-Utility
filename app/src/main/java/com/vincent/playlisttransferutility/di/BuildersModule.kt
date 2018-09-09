package com.vincent.playlisttransferutility.di

import com.vincent.playlisttransferutility.ui.MainActivity
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModule
import com.vincent.playlisttransferutility.ui.main.MainFragment
import com.vincent.playlisttransferutility.ui.main.MainModule
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionFragment
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [PlaylistSelectionModule::class])
    abstract fun bindPlaylistSelectionFragment(): PlaylistSelectionFragment

    @ContributesAndroidInjector(modules = [GoogleLoginModule::class])
    abstract fun bindGoogleLoginFragment(): GoogleLoginDialogFragment
}