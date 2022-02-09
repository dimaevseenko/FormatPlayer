package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentChannelsBinding
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Genre
import ua.dimaevseenko.format_player.model.LastWatchedChannel
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class ChannelsFragment @Inject constructor(): AnimatedFragment(), TabLayout.OnTabSelectedListener, VerticalChannelRecyclersAdapter.Listener {

    companion object{
        const val TAG = "ChannelsFragment"
    }

    private lateinit var binding: FragmentChannelsBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var verticalChannelsAdapterFactory: VerticalChannelsAdapter.Factory
    @Inject lateinit var verticalChannelRecyclersAdapterFactory: VerticalChannelRecyclersAdapter.Factory

    private var lastFocusView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChannelsBinding.bind(inflater.inflate(R.layout.fragment_channels, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartX()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        loadGenres()

        binding.channelsGenresTabLayout?.addOnTabSelectedListener(this)
        binding.backCard.setOnClickListener { dismiss() }

        loadRecycler()
    }

    private fun loadRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            verticalChannelsAdapterFactory.createVerticalChannelsAdapter(playlistViewModel.getChannels()!!).apply {
                setListener(this@ChannelsFragment)
            }
        else
            verticalChannelRecyclersAdapterFactory.createAdapter(playlistViewModel.getGenres()!!, playlistViewModel.getChannels()!!).apply {
                setListener(this@ChannelsFragment)
            }
        binding.channelsGenresTabLayout?.let { onTabSelected(it.getTabAt(it.selectedTabPosition)) }
    }

    private fun loadGenres(){
        playlistViewModel.getGenres()?.forEach { genre ->
                binding.channelsGenresTabLayout?.let{
                    if(playlistViewModel.getChannels()!!.getChannelsForGenre(genre.id).size>0)
                        it.addTab(createTab(genre))
                }
        }
    }
    private fun createTab(genre: Genre): TabLayout.Tab{
        return binding.channelsGenresTabLayout!!.newTab().apply {
            this.text = genre.name
            this.id = genre.id.toInt()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(binding.recyclerView.adapter is VerticalChannelsAdapter)
            (binding.recyclerView.adapter as VerticalChannelsAdapter).updateChannels(playlistViewModel.getChannels()!!.getChannelsForGenre(tab!!.id.toString()))
        (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            binding.channelsGenresTabLayout?.getTabAt(it.getInt("genrePosition"))?.select()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.channelsGenresTabLayout?.let { outState.putInt("genrePosition", it.selectedTabPosition) }
    }

    override fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?) {
        addToLast(channel)

        binding.backCard.setOnClickListener {}

        if(requireContext().isTV)
            focusedView?.let { lastFocusView = it }

        playerFragment.startStream(channel){
            binding.backCard.setOnClickListener { dismiss() }
            if(requireContext().isTV)
                lastFocusView?.requestFocus()
        }
    }

    private fun addToLast(channel: Channel){
        Config.Values.lastWatchedChannelsIds.add(LastWatchedChannel(
            channel.id,
            System.currentTimeMillis()
        ))

        Config.Values.lastWatchedChannelsIds.sortBy { it.dateAdded }
        Config.Values.save(requireContext())

        updateRecycler()
    }

    private fun updateRecycler(){
        if(binding.channelsGenresTabLayout?.getTabAt(0)?.id.toString() != "-1"){
            binding.channelsGenresTabLayout?.addTab(createTab(Genre("-1", getString(R.string.last))), 0)
        }

        if(binding.recyclerView.adapter is VerticalChannelRecyclersAdapter) {
            if(binding.recyclerView.findViewHolderForAdapterPosition(0) != null)
                (binding.recyclerView.adapter as VerticalChannelRecyclersAdapter).updateLastChannels(
                    (binding.recyclerView.findViewHolderForAdapterPosition(0) as VerticalChannelRecyclersAdapter.ViewHolder).binding.recyclerView,
                    playlistViewModel.getChannels()!!.getChannelsForGenre("-1")
                )
        }
        else if(binding.channelsGenresTabLayout?.getTabAt(binding.channelsGenresTabLayout?.selectedTabPosition!!)?.id.toString() == "-1") {
            (binding.recyclerView.adapter as VerticalChannelsAdapter).updateChannels(
                playlistViewModel.getChannels()!!.getChannelsForGenre("-1")
            )
            (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
        }
    }

    override fun onVerticalFocusChanged(position: Int) {
        val lm = (binding.recyclerView.layoutManager as LinearLayoutManager)

        val smoothScroller = getLinearSmoothScroller()

        val firstVisible = lm.findFirstCompletelyVisibleItemPosition()
        val lastVisible = lm.findLastCompletelyVisibleItemPosition()

        val itemsVisible = (lastVisible - firstVisible)

        if(position >= itemsVisible) {
            smoothScroller.targetPosition = position - itemsVisible
            lm.startSmoothScroll(smoothScroller)
        }
    }

    private fun getLinearSmoothScroller(): LinearSmoothScroller {
        return object : LinearSmoothScroller(context){
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }

    fun dismiss(){
        if(isAnimated) {
            animateEndX()
            (parentFragment as HomeFragment).homeWaysFragment()
        }
    }
}