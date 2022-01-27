package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewChannelsVerticalItemBinding
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

class VerticalChannelsAdapter @AssistedInject constructor(
    @Assisted("channels")
    private val channels: Channels,
    private val context: Context
): RecyclerChannelsAdapter(
    channels
) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_vertical_item, parent, false))
    }

    class ViewHolder(view: View): RecyclerChannelsAdapter.ViewHolder(view){
        private var binding = RecyclerViewChannelsVerticalItemBinding.bind(view)

        override fun bind(channel: Channel) {
            binding.channelImageView.setImageBitmap(channel.imageBitmap)
            binding.channelNameTextView.text = "${channel.id} | ${channel.name}"
        }
    }

    @AssistedFactory
    interface Factory{
        fun createVerticalChannelsAdapter(
            @Assisted("channels")
            channels: Channels
        ): VerticalChannelsAdapter
    }
}