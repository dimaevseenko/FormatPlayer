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
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels

class HorizontalChannelsAdapter @AssistedInject constructor(
    @Assisted("channels")
    private val channels: Channels,
    private val context: Context
): RecyclerChannelsAdapter(
    channels
) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_horizontal_item, parent, false))
    }

    class ViewHolder(view: View): RecyclerChannelsAdapter.ViewHolder(view){
        private var binding = RecyclerViewChannelsHorizontalItemBinding.bind(view)

        override fun bind(channel: Channel, listener: Listener?) {
            binding.channelImageView.setImageBitmap(channel.imageBitmap)
            binding.channelLayout.setOnClickListener { listener?.onSelectedChannel(channel, absoluteAdapterPosition) }
            binding.channelLayout.setOnFocusChangeListener { view, hasFocus ->
                if(hasFocus) {
                    listener?.onHorizontalFocusChanged(absoluteAdapterPosition)
                    binding.root.startAnimation(
                        ScaleAnimation(
                            1f,
                            1.10f,
                            1f,
                            1.10f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f
                        ).apply {
                            fillAfter = true
                            duration = 150
                        })
                }else{
                    binding.root.startAnimation(
                        ScaleAnimation(
                            1.10f,
                            1f,
                            1.10f,
                            1f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f
                        ).apply {
                            fillAfter = true
                            duration = 150
                        }
                    )
                }
            }
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