package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

abstract class RecyclerChannelsAdapter constructor(
    private var channels: Channels
): RecyclerView.Adapter<RecyclerChannelsAdapter.ViewHolder>() {

    fun updateChannels(channels: Channels){
        val diffUtils = DiffUtil.calculateDiff(DiffUtils(this.channels, channels))
        this.channels = channels
        diffUtils.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RecyclerChannelsAdapter.ViewHolder, position: Int) {
        holder.bind(channels.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerChannelsAdapter.ViewHolder{
        return getViewHolder(parent, viewType)
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerChannelsAdapter.ViewHolder

    override fun getItemCount(): Int {
        return channels.size
    }

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun bind(channel: Channel)
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