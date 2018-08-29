package com.vincent.playlisttransferutility.ui.googlelogin.di

import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginDialogFragment
import dagger.Subcomponent

@Subcomponent(modules = [GoogleLoginModule::class])
@GoogleLoginScope
interface GoogleLoginComponent {

    fun inject(fragment: GoogleLoginDialogFragment)
}