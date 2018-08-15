package com.vincent.playlisttransferutility.pages.playlistselection

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.databinding.FragmentPlaylistSelectionBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_playlist_selection.*

class PlaylistSelectionFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: PlaylistSelectionViewModel

    private lateinit var playlistSelectionAdapter: PlaylistSelectionAdapter
    private lateinit var playlistSelectionList: RecyclerView
    private lateinit var musicServiceSelectorOne: AppCompatSpinner
    private lateinit var musicServiceSelectorTwo: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_playlist_selection, container, false)

        viewModel = ViewModelProviders.of(this).get(PlaylistSelectionViewModel::class.java)
        val binding: FragmentPlaylistSelectionBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_playlist_selection, container, false)
        binding.viewModel = viewModel
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlaylistSelectionList()
        setupServiceSelectionSpinners()
    }

    private fun setupServiceSelectionSpinners() {
        musicServiceSelectorOne = sp_service_selection_one
        musicServiceSelectorTwo = sp_service_selection_two

        val spinnerAdapter: ArrayAdapter<MusicService> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item,
                MusicService.values())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        musicServiceSelectorOne.adapter = spinnerAdapter
        musicServiceSelectorTwo.adapter = spinnerAdapter
    }

    private fun setupPlaylistSelectionList() {
        playlistSelectionAdapter = PlaylistSelectionAdapter(viewModel, context!!)
        playlistSelectionList = rv_playlist_selection_list
        playlistSelectionList.layoutManager = LinearLayoutManager(context!!)
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
        compositeDisposable.add(viewModel.getPlaylistsEvents()
                .subscribe(this::onPlaylistsReceived) {})
    }

    private fun onPlaylistsReceived(playlists: List<Playlist>) {
        playlistSelectionAdapter.setPlaylists(playlists)
    }
}