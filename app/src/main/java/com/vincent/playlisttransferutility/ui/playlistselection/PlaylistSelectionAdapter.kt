package com.vincent.playlisttransferutility.ui.playlistselection

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vincent.playlisttransferutility.AppComponent
import com.vincent.playlisttransferutility.BR
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.Playlist
import com.vincent.playlisttransferutility.utils.resources.ResourceProvider
import kotlinx.android.synthetic.main.playlist_list_view_item.view.*

class PlaylistSelectionAdapter(viewModel: PlaylistSelectionViewModel,
                               context: Context) :
        RecyclerView.Adapter<PlaylistSelectionAdapter.PlaylistSelectionViewHolder>() {

    private val playlists: ArrayList<Playlist> = ArrayList()
    val context: Context
    private val layoutInflater: LayoutInflater
    private val viewModel: PlaylistSelectionViewModel

    init {
        this.viewModel = viewModel
        this.context = context
        this.playlists.addAll(playlists)
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSelectionViewHolder {
        val view = layoutInflater.inflate(R.layout.playlist_list_view_item, parent, false)
        return PlaylistSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistSelectionViewHolder, position: Int) {
        val playlist: Playlist = playlists[position]

        holder.viewBinding!!.setVariable(BR.viewModel, viewModel)
        holder.viewBinding.setVariable(BR.playlistId, playlist.id)
        holder.viewBinding.executePendingBindings()

        holder.setTitle(playlist.name)
        holder.setCoverArt(playlist.coverArtUrl)
        holder.setPlaylistCount(playlist.trackCount)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    fun setPlaylists(playlists: List<Playlist>) {
        this.playlists.clear()
        this.playlists.addAll(playlists)
        notifyDataSetChanged()
    }

    class PlaylistSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val playlistCoverArtView: ImageView
        private val playlistTitleTextView: TextView
        private val playlistCountTextView: TextView
        private val checkBox: CheckBox
        private val resourceProvider: ResourceProvider

        val viewBinding: ViewDataBinding?

        init {
            playlistCoverArtView = itemView.iv_playlist_cover_art
            playlistTitleTextView = itemView.tv_playlist_title
            playlistCountTextView = itemView.tv_playlist_count
            checkBox = itemView.cb_playlist_select
            viewBinding = DataBindingUtil.bind(itemView)
            resourceProvider = AppComponent.instance.resourceProvider
        }

        //TODO: placeholder (or profile pic) for google play music
        fun setCoverArt(imageUrl: String?) {
            if (imageUrl == null) {
                return
            }

            val requestOptions: RequestOptions = RequestOptions().centerCrop()
            Glide.with(itemView)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .into(playlistCoverArtView)
        }

        fun setTitle(title: String) {
            playlistTitleTextView.setText(title)
        }

        fun setPlaylistCount(count: Int) {
            if (count <= 0) {
                return
            }

            playlistCountTextView.text = itemView.context
                    .getString(R.string.playlist_selection_playlist_track_count, count)
        }
    }
}