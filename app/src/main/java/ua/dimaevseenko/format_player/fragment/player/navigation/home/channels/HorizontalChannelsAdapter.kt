package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
    @Assisted("layoutManager")
    private val linearLayoutManager: LinearLayoutManager,
    private val context: Context
): RecyclerChannelsAdapter(
    channels,
    linearLayoutManager
) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_horizontal_item, parent, false))
    }

    class ViewHolder(view: View): RecyclerChannelsAdapter.ViewHolder(view){
        private var binding = RecyclerViewChannelsHorizontalItemBinding.bind(view)

        override fun bind(channel: Channel) {
            binding.channelImageView.setImageBitmap(channel.imageBitmap)
        }
    }

    @AssistedFactory
    interface Factory{
        fun createHorizontalChannelsAdapter(
            @Assisted("channels")
            channels: Channels,
            @Assisted("layoutManager")
            linearLayoutManager: LinearLayoutManager
        ): HorizontalChannelsAdapter
    }
}