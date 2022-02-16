package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewChannelsHorizontalItemBinding
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

class HorizontalChannelsAdapter @AssistedInject constructor(
    @Assisted("channels")
    private val channels: Channels,
    private val context: Context,
    private val focusScaleAnimator: FocusScaleAnimator
): RecyclerChannelsAdapter(
    channels
) {

    interface Listener: RecyclerChannelsAdapter.Listener{
        fun onHorizontalFocusChanged(position: Int)
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_horizontal_item, parent, false), focusScaleAnimator)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0)
            1
        else if(position == channels.size-1)
            2
        else
            0
    }

    class ViewHolder(view: View, private val focusScaleAnimator: FocusScaleAnimator): RecyclerChannelsAdapter.ViewHolder(view){
        private var binding = RecyclerViewChannelsHorizontalItemBinding.bind(view)

        override fun bind(channel: Channel, listener: RecyclerChannelsAdapter.Listener?) {
            binding.channelImageView.setImageBitmap(channel.imageBitmap)
            binding.channelLayout.setOnClickListener { listener?.onSelectedChannel(channel, absoluteAdapterPosition, binding.channelLayout) }
            binding.channelLayout.setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus) {
                    if(listener is Listener)
                        listener.onHorizontalFocusChanged(absoluteAdapterPosition)

                    focusScaleAnimator.animate(binding.card, true)
                }else
                    focusScaleAnimator.animate(binding.card, false)
            }

            if(itemViewType == 1)
                binding.channelLayout.nextFocusLeftId = binding.channelLayout.id
            else if(itemViewType == 2)
                binding.channelLayout.nextFocusRightId = binding.channelLayout.id
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