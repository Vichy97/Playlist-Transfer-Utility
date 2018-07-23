package com.vincent.playlisttransferutility.pages.playlistselection

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.spotify.Playlist
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_playlist_selection.*

class PlaylistSelectionActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: PlaylistSelectionViewModel

    private lateinit var playlistSelectionAdapter: PlaylistSelectionAdapter
    private lateinit var playlistSelectionList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_selection)

        viewModel = ViewModelProviders.of(this).get(PlaylistSelectionViewModel::class.java)
        setupPlaylistSelectionList()
    }

    fun setupPlaylistSelectionList() {
        playlistSelectionAdapter = PlaylistSelectionAdapter(viewModel, this)
        playlistSelectionList = rv_playlist_selection_list
        playlistSelectionList.layoutManager = LinearLayoutManager(this)
        playlistSelectionList.adapter = playlistSelectionAdapter
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()

        subscribeToViewModelEvents()
    }

    fun subscribeToViewModelEvents() {
        compositeDisposable.add(viewModel.getPlaylistsEvent().subscribe(this::onPlaylistsRecieved))
    }

    private fun onPlaylistsRecieved(playlists: List<Playlist>) {
        playlistSelectionAdapter.setPlaylists(playlists)
    }
}