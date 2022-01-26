package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

abstract class RecyclerChannelsAdapter constructor(
    private val channels: Channels
): RecyclerView.Adapter<RecyclerChannelsAdapter.ViewHolder>() {

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
}