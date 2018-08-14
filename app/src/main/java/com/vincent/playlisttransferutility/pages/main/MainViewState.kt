package com.vincent.playlisttransferutility.pages.main

data class MainViewState(var spotifyLogin: Boolean = false,
                    var googlePlayMusicLogin: Boolean = false,
                    var appleMusicLogin: Boolean = false)