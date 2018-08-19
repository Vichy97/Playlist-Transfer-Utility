package com.vincent.playlisttransferutility.ui.googlelogin.di

import com.vincent.playlisttransferutility.ui.googlelogin.GoogleLoginModel
import dagger.Subcomponent

@Subcomponent(modules = [GoogleLoginModule::class])
@GoogleLoginScope
interface GoogleLoginComponent {

    val googleLoginModel: GoogleLoginModel
}