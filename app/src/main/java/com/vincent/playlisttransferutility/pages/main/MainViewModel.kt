package com.vincent.playlisttransferutility.pages.main

import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.BuildConfig
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.spotify.AuthToken
import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyAuthenticationRequestScope
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel() {

    companion object {
        const val SPOTIFY_LOGIN_REQUEST_CODE: Int = 1337
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mainModel: MainModel
    private val resourceProvider: ResourceProvider

    private val toastMessageSubject: PublishSubject<String> = PublishSubject.create()
    private val spotifyLoginRequestSubject: PublishSubject<AuthenticationRequest> = PublishSubject.create()
    private val viewStateSubject: BehaviorSubject<MainViewState> = BehaviorSubject.create()
    private val navigateSubject: PublishSubject<Boolean> = PublishSubject.create()

    private lateinit var viewState: MainViewState

    init {
        val mainComponent: MainComponent = DaggerMainComponent.builder().build()
        mainModel = mainComponent.getMainModel()
        resourceProvider = mainComponent.getResourceProvider()

        initViewState()
    }

    private fun initViewState() {
        viewState = MainViewState()

        //TODO: fetch all auth tokens from model
        compositeDisposable.add(mainModel.getSpotifyAuthToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.spotifyLogin = (it != null)
                    viewStateSubject.onNext(viewState)
                })
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    //region Events
    fun getToastMessageEvents(): Observable<String> {
        return toastMessageSubject
    }

    fun getSpotifyLoginRequestEvents(): Observable<AuthenticationRequest> {
        return spotifyLoginRequestSubject
    }

    fun getViewStateEvents(): Observable<MainViewState> {
        return viewStateSubject
    }

    fun getNavigationEvents(): Observable<Boolean> {
        return navigateSubject
    }
    //endregion Events

    //region Activity Results
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPOTIFY_LOGIN_REQUEST_CODE) {
            onSpotifyLogin(data, resultCode)
        }
    }

    private fun onSpotifyLogin(data: Intent?, resultCode: Int) {
        val response = AuthenticationClient.getResponse(resultCode, data)
        when (response.type) {
            AuthenticationResponse.Type.TOKEN -> {
                onSpotifyTokenReceived(response)
            }
            else -> {
                toastMessageSubject.onNext(resourceProvider.getString(R.string.login_error))
            }
        }
    }

    private fun onSpotifyTokenReceived(response: AuthenticationResponse) {
        val authToken: AuthToken = AuthToken.fromAuthenticationResponse(response)
        mainModel.saveSpotifyAuthToken(authToken)
        viewState.spotifyLogin = true
        viewStateSubject.onNext(viewState)
    }
    //endregion Activity Results

    //region View Events
    fun onSpotifyClicked() {
        spotifyLoginRequestSubject.onNext(getSpotifyAuthenticationRequest())
    }

    fun onAppleMusicClicked() {

    }

    fun onGooglePlayMusicClicked() {
        toastMessageSubject.onNext("Google Play Music Clicked")
    }

    fun onStartTransferClicked() {
        navigateSubject.onNext(true)
    }
    //endregion View Events

    private fun getSpotifyAuthenticationRequest(): AuthenticationRequest {
        val builder: AuthenticationRequest.Builder =
                AuthenticationRequest.Builder(BuildConfig.SPOTIFY_CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN, getSpotifyRedirectUri())
        val scopes: Array<String> = arrayOf(SpotifyAuthenticationRequestScope.MODIFY_PLAYLIST_PUBLIC.toString(),
                SpotifyAuthenticationRequestScope.MODIFY_PLAYLIST_PRIVATE.toString())
        builder.setScopes(scopes).setShowDialog(true)

        return builder.build()
    }

    private fun getSpotifyRedirectUri(): String {
        val scheme: String = resourceProvider.getString(R.string.uri_scheme)
        val host: String = resourceProvider.getString(R.string.uri_host_main)
        return "$scheme://$host"
    }
}