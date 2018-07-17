package com.vincent.playlisttransferutility.pages.main

import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class MainViewModel(mainModel: MainModel) {

    private var subject: BehaviorSubject<String> = BehaviorSubject.create()

    fun getToastMessage(): Observable<String> {
        return subject
    }

    fun onSpotifyClicked() {
        subject.onNext("Spotify Clicked")
    }

    fun onAppleMusicClicked() {
        subject.onNext("Apple Music Clicked")
    }

    fun onGooglePlayMusicClicked() {
        subject.onNext("Google Play Music Clicked")
    }

    fun onStartTransferClicked() {
        subject.onNext("Start Transfer Clicked")
    }
}