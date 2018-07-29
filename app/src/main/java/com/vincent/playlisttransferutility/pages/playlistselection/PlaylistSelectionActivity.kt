package com.vincent.playlisttransferutility.pages.playlistselection

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.databinding.ActivityPlaylistSelectionBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_playlist_selection.*

class PlaylistSelectionActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: PlaylistSelectionViewModel

    private lateinit var playlistSelectionAdapter: PlaylistSelectionAdapter
    private lateinit var playlistSelectionList: RecyclerView
    private lateinit var musicServiceSelectorOne: AppCompatSpinner
    private lateinit var musicServiceSelectorTwo: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_selection)

        viewModel = ViewModelProviders.of(this).get(PlaylistSelectionViewModel::class.java)

        setupDataBinding()
        setupPlaylistSelectionList()
        setupServiceSelectionSpinners()
    }

    private fun setupDataBinding() {
        val binding: ActivityPlaylistSelectionBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_playlist_selection)
        binding.viewModel = viewModel
    }

    private fun setupServiceSelectionSpinners() {
        musicServiceSelectorOne = sp_service_selection_one
        musicServiceSelectorTwo = sp_service_selection_two
        val spinnerAdapter: ArrayAdapter<MusicService> = ArrayAdapter(this, android.R.layout.simple_spinner_item, MusicService.values())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        musicServiceSelectorOne.adapter = spinnerAdapter
        musicServiceSelectorTwo.adapter = spinnerAdapter
    }

    private fun setupPlaylistSelectionList() {
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

    private fun subscribeToViewModelEvents() {
        compositeDisposable.add(viewModel.getPlaylistsEvents().subscribe(this::onPlaylistsReceived))
    }

    private fun onPlaylistsReceived(playlists: List<Playlist>) {
        playlistSelectionAdapter.setPlaylists(playlists)
    }
}