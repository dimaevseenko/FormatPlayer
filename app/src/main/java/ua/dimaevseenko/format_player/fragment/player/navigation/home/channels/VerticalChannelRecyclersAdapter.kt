package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewChannelsHorizontalBinding
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Channels
import ua.dimaevseenko.format_player.model.Genre
import ua.dimaevseenko.format_player.model.Genres

class VerticalChannelRecyclersAdapter @AssistedInject constructor(
    @Assisted("genres")
    private val genres: Genres,
    @Assisted("channels")
    private val channels: Channels,
    private val horizontalChannelsAdapterFactory: HorizontalChannelsAdapter.Factory,
    private val context: Context
): RecyclerView.Adapter<VerticalChannelRecyclersAdapter.ViewHolder>(){

    private var listener: Listener? = null

    fun setListener(listener: Listener){
        this.listener = listener
    }

    interface Listener: RecyclerChannelsAdapter.Listener{
        fun onVerticalFocusChanged(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_channels_horizontal, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            context,
            genres[position],
            horizontalChannelsAdapterFactory.createHorizontalChannelsAdapter(channels.getChannelsForGenre(genres[position].id)),
            listener
        )
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view), HorizontalChannelsAdapter.Listener{
        private var binding = RecyclerViewChannelsHorizontalBinding.bind(view)

        private lateinit var context: Context
        private var listener: RecyclerChannelsAdapter.Listener? = null

        fun bind(context: Context, genre: Genre, horizontalChannelsAdapter: HorizontalChannelsAdapter, listener: Listener?){
            horizontalChannelsAdapter.apply {
                setListener(this@ViewHolder)
            }
            this.context = context
            this.listener = listener

            binding.genreName.text = genre.name
            initRecycler(context, horizontalChannelsAdapter)
        }

        private fun initRecycler(context: Context, horizontalChannelsAdapter: HorizontalChannelsAdapter){
            binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false).apply {
                stackFromEnd = false
            }
            binding.recyclerView.adapter = horizontalChannelsAdapter
        }

        override fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?) {
            listener?.onSelectedChannel(channel, position, focusedView)
        }

        override fun onHorizontalFocusChanged(position: Int) {
            if(listener is Listener)
                (listener as? Listener)?.onVerticalFocusChanged(absoluteAdapterPosition)

            val lm = (binding.recyclerView.layoutManager as LinearLayoutManager)

            val first = lm.findFirstVisibleItemPosition()
            val last = lm.findLastVisibleItemPosition()

            val center = (last - first)/2

            val smoothScroller = getLinearSmoothScroller()

            if(position >= center) {
                smoothScroller.targetPosition = position-center
                lm.startSmoothScroll(smoothScroller)
            }

        }

        private fun getLinearSmoothScroller(): LinearSmoothScroller {
            return object : LinearSmoothScroller(context){
                override fun getHorizontalSnapPreference(): Int {
                    return LinearSmoothScroller.SNAP_TO_START
                }
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("genres")
            genres: Genres,
            @Assisted("channels")
            channels: Channels
        ): VerticalChannelRecyclersAdapter
    }
}