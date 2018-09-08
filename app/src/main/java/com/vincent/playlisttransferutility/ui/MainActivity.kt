package com.vincent.playlisttransferutility.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}