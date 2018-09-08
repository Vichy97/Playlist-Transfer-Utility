package com.vincent.playlisttransferutility.data.spotify.models.request

enum class SpotifyAuthenticationRequestScope(private val stringValue: String) {
    MODIFY_PLAYLIST_PRIVATE("playlist-modify-private"),
    MODIFY_PLAYLIST_PUBLIC("playlist-modify-public");

    override fun toString(): String {
        return stringValue
    }
}