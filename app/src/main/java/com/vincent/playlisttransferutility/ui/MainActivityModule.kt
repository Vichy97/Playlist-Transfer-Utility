package com.vincent.playlisttransferutility.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.di.scopes.PerActivity
import com.vincent.playlisttransferutility.di.scopes.PerFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModule
import com.vincent.playlisttransferutility.ui.main.MainFragment
import com.vincent.playlisttransferutility.ui.main.MainModule
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionFragment
import com.vincent.playlisttransferutility.ui.playlistselection.PlaylistSelectionModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        fun provideNavController(mainActivity: MainActivity): NavController {
            return Navigation.findNavController(mainActivity, R.id.nav_host)
        }
    }

    @PerFragment
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainFragment(): MainFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PlaylistSelectionModule::class])
    abstract fun bindPlaylistSelectionFragment(): PlaylistSelectionFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [GoogleLoginModule::class])
    abstract fun bindGoogleLoginFragment(): GoogleLoginDialogFragment
}