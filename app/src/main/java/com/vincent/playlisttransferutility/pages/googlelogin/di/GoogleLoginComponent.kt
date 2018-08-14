package com.vincent.playlisttransferutility.pages.googlelogin.di

import com.vincent.playlisttransferutility.pages.googlelogin.GoogleLoginModel
import dagger.Subcomponent

@Subcomponent(modules = [GoogleLoginModule::class])
@GoogleLoginScope
interface GoogleLoginComponent {

    val googleLoginModel: GoogleLoginModel
}