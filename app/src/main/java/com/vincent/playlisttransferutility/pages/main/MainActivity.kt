package com.vincent.playlisttransferutility.pages.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.SpotifyNativeAuthUtil
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel = MainViewModel(MainModel())

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDataBinding()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mainViewModel.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupDataBinding() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.add(mainViewModel.getToastMessage().subscribe {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        compositeDisposable.add(mainViewModel.getSpotifyLogin().subscribe {
            loginToSpotify(it)
        })
    }

    private fun loginToSpotify(request: AuthenticationRequest) {
        AuthenticationClient.openLoginActivity(this, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }
}
