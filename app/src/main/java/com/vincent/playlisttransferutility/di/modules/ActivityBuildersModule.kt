package com.vincent.playlisttransferutility.di.modules

import com.vincent.playlisttransferutility.di.scopes.PerActivity
import com.vincent.playlisttransferutility.ui.MainActivity
import com.vincent.playlisttransferutility.ui.MainActivityModule
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModule
import com.vincent.playlisttransferutility.ui.main.MainFragment
import com.vincent.playlisttransferutility.ui.main.MainModule
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionFragment
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}