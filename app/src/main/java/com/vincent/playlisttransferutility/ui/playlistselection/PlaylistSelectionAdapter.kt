package com.vincent.playlisttransferutility.ui.playlistselection

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.data.models.Playlist
import kotlinx.android.synthetic.main.playlist_list_view_item.view.*

class PlaylistSelectionAdapter(private val viewModel: PlaylistSelectionViewModel,
                               private val layoutInflater: LayoutInflater)
    : RecyclerView.Adapter<PlaylistSelectionAdapter.PlaylistSelectionViewHolder>() {

    private val playlists: ArrayList<Playlist> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSelectionViewHolder {
        val view = layoutInflater.inflate(R.layout.playlist_list_view_item, parent, false)
        return PlaylistSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistSelectionViewHolder, position: Int) {
        val playlist: Playlist = playlists[position]

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

        init {
            playlistCoverArtView = itemView.iv_playlist_cover_art
            playlistTitleTextView = itemView.tv_playlist_title
            playlistCountTextView = itemView.tv_playlist_count
            checkBox = itemView.cb_playlist_select
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
            playlistTitleTextView.text = title
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