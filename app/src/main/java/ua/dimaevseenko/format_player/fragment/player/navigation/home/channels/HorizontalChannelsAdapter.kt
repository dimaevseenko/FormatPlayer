package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewChannelsHorizontalItemBinding
import ua.dimaevseenko.format_player.di.module.FocusAnimation
import ua.dimaevseenko.format_player.di.module.UnFocusAnimation
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels
import javax.inject.Inject

class HorizontalChannelsAdapter @AssistedInject constructor(
    @Assisted("channels")
    private val channels: Channels,
    private val context: Context,
    private val channelFocusListener: ChannelFocusListener
): RecyclerChannelsAdapter(
    channels
) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_horizontal_item, parent, false), channelFocusListener)
    }

    class ViewHolder(view: View, private val channelFocusListener: ChannelFocusListener): RecyclerChannelsAdapter.ViewHolder(view){
        private var binding = RecyclerViewChannelsHorizontalItemBinding.bind(view)

        override fun bind(channel: Channel, listener: Listener?) {
            binding.channelImageView.setImageBitmap(channel.imageBitmap)
            binding.channelLayout.setOnClickListener { listener?.onSelectedChannel(channel, absoluteAdapterPosition) }
            binding.channelLayout.onFocusChangeListener = channelFocusListener
        }
    }

    @AssistedFactory
    interface Factory{
        fun createHorizontalChannelsAdapter(
            @Assisted("channels")
            channels: Channels
        ): HorizontalChannelsAdapter
    }
}