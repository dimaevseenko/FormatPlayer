package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

abstract class RecyclerChannelsAdapter constructor(
    private var channels: Channels
): RecyclerView.Adapter<RecyclerChannelsAdapter.ViewHolder>() {

    private var listener: Listener? = null

    fun updateChannels(channels: Channels){
        val diffUtils = DiffUtil.calculateDiff(DiffUtils(this.channels, channels))
        this.channels = channels
        diffUtils.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(channels.get(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return getViewHolder(parent, viewType)
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    override fun getItemCount(): Int {
        return channels.size
    }

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun bind(channel: Channel, listener: Listener?)
    }

    fun setListener(listener: Listener){
        this.listener = listener
    }

    interface Listener{
        fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?)
    }

    private class DiffUtils(
        private val oldChannels: Channels,
        private val newChannels: Channels
    ): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldChannels.size
        }

        override fun getNewListSize(): Int {
            return newChannels.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldChannels[oldItemPosition].id == newChannels[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldChannels[oldItemPosition].url == newChannels[newItemPosition].url
        }
    }
}