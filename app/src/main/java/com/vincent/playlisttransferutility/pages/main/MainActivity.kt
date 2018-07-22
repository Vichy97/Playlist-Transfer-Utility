package com.vincent.playlisttransferutility.pages.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.ActivityMainBinding
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mainViewModel: MainViewModel
    private val resourceProvider: ResourceProvider

    private lateinit var spotifyButton: Button
    private lateinit var googlePlayMusicButton: Button
    private lateinit var appleMusicButton: Button

    init {
        val component: MainComponent = DaggerMainComponent.builder().build()

        mainViewModel = component.getMainViewModel()
        resourceProvider = component.getResourceProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDataBinding()

        spotifyButton = btn_spotify_button
        googlePlayMusicButton = btn_google_play_music_button
        appleMusicButton = btn_apple_music_button
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

        subscribeToViewModelEvents()
    }

    private fun subscribeToViewModelEvents() {
        compositeDisposable.add(mainViewModel
                .getToastMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
        compositeDisposable.add(mainViewModel.getSpotifyLoginRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loginToSpotify(it)
                })
        compositeDisposable.add(mainViewModel.getViewState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                })
    }

    private fun loginToSpotify(request: AuthenticationRequest) {
        AuthenticationClient.openLoginActivity(this, MainViewModel.SPOTIFY_LOGIN_REQUEST_CODE, request)
    }
}
