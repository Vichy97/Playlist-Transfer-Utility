package com.vincent.playlisttransferutility.ui.playlistselection

import android.content.Context
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
import androidx.lifecycle.ViewModelProviders
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.MusicService
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.databinding.FragmentPlaylistSelectionBinding
import com.vincent.playlisttransferutility.ui.base.BaseFragment
import com.vincent.playlisttransferutility.ui.base.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_playlist_selection.*
import javax.inject.Inject

class PlaylistSelectionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: PlaylistSelectionViewModel

    private lateinit var playlistSelectionAdapter: PlaylistSelectionAdapter
    private lateinit var playlistSelectionList: RecyclerView
    private lateinit var musicServiceSelectorOne: AppCompatSpinner
    private lateinit var musicServiceSelectorTwo: Spinner

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[PlaylistSelectionViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentPlaylistSelectionBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_playlist_selection, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
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
        playlistSelectionAdapter = PlaylistSelectionAdapter(viewModel, LayoutInflater.from(context!!))
        playlistSelectionList = rv_playlist_selection_list
        playlistSelectionList.layoutManager = LinearLayoutManager(context!!)
        playlistSelectionList.adapter = playlistSelectionAdapter
    }

    override fun subscribeToViewModelEvents() {
        compositeDisposable.add(viewModel.getPlaylistsEvents()
                .subscribe(this::onPlaylistsReceived) {})
    }

    private fun onPlaylistsReceived(playlists: List<Playlist>) {
        playlistSelectionAdapter.setPlaylists(playlists)
    }
}