package com.vincent.playlisttransferutility.pages.main

import androidx.lifecycle.ViewModel
import android.content.Intent
import android.util.Log
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.BuildConfig
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.AuthToken
import com.vincent.playlisttransferutility.data.models.spotify.request.SpotifyAuthenticationRequestScope
import com.vincent.playlisttransferutility.pages.main.di.MainModule
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import com.vincent.playlisttransferutility.utils.rx.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel() {

    companion object {
        const val SPOTIFY_LOGIN_REQUEST_CODE: Int = 1337
        const val TAG: String = "MainViewModel"
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mainModel: MainModel
    private val resourceProvider: ResourceProvider
    private val schedulersProvider: SchedulersProvider

    private val toastMessageSubject: PublishSubject<String> = PublishSubject.create()
    private val spotifyLoginRequestSubject: PublishSubject<AuthenticationRequest> = PublishSubject.create()
    private val viewStateSubject: BehaviorSubject<MainViewState> = BehaviorSubject.create()

    private lateinit var viewState: MainViewState

    init {
        mainModel =  AppComponent.instance.newMainComponent(MainModule()).mainModel
        resourceProvider = AppComponent.instance.resourceProvider
        schedulersProvider = AppComponent.instance.schedulersProvider

        initViewState()
    }

    private fun initViewState() {
        compositeDisposable.add(Single.zip(
                mainModel.getSpotifyAuthToken(),
                mainModel.getGooglePlayAuthToken(),
                BiFunction { spotifyAuthToken: AuthToken,
                             googlePlayAuthToken: AuthToken ->
                    {
                        MainViewState(spotifyAuthToken.accessToken.isNotEmpty(),
                                googlePlayAuthToken.accessToken.isNotEmpty())
                    }
                })
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({
                    viewState = it.invoke()
                    viewStateSubject.onNext(viewState)
                }, {
                    Log.e("MainViewModel", "Error subscribing to Model Events", it)
                }))
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
        compositeDisposable.add(mainModel.saveSpotifyAuthToken(response)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({

                }, {
                    Log.e(TAG, "Error Fetching Spotify User", it)
                    toastMessageSubject.onNext("Error Fetching Spotify User")
                }))
        viewState.spotifyLogin = true
        viewStateSubject.onNext(viewState)
    }
    //endregion Activity Results

    //region View Events
    fun onSpotifyClicked() {
        spotifyLoginRequestSubject.onNext(getSpotifyAuthenticationRequest())
    }

    fun onAppleMusicClicked() {
        //TODO: apple music authentication
    }

    fun onGooglePlayMusicClicked() {

    }

    fun onStartTransferClicked() {

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