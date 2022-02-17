package ua.dimaevseenko.format_player.fragment.player.navigation.search

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentSearchBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.HorizontalChannelsAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.RecyclerChannelsAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.VerticalChannelRecyclersAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.VerticalChannelsAdapter
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.LastWatchedChannel
import ua.dimaevseenko.format_player.playerFragment
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class SearchFragment @Inject constructor(): AnimatedFragment(), HorizontalChannelsAdapter.Listener {

    companion object{
        const val TAG = "SearchFragment"
    }

    private lateinit var binding: FragmentSearchBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var verticalChannelsAdapterFactory: VerticalChannelsAdapter.Factory
    @Inject lateinit var horizontalChannelsAdapterFactory: HorizontalChannelsAdapter.Factory

    private lateinit var recyclerChannelsAdapter: RecyclerChannelsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartY()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        initView()
    }

    private fun initView(){
        binding.searchEditText.addTextChangedListener(SearchTextWatcher())
        binding.recyclerView.layoutManager = createLinearLayoutManager()
        binding.recyclerView.adapter = createRecyclerAdapter()

        if(requireContext().isTV)
            binding.searchEditText.requestFocus()
    }

    private fun createLinearLayoutManager(): LinearLayoutManager{
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(requireContext())
        else
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createRecyclerAdapter(): RecyclerChannelsAdapter{
        recyclerChannelsAdapter = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            verticalChannelsAdapterFactory.createVerticalChannelsAdapter(playlistViewModel.getChannels()!!)
        else
            horizontalChannelsAdapterFactory.createHorizontalChannelsAdapter(playlistViewModel.getChannels()!!)
        return recyclerChannelsAdapter.apply { setListener(this@SearchFragment) }
    }

    override fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?) {
        Config.Values.lastWatchedChannelsIds.add(LastWatchedChannel(channel.id, System.currentTimeMillis()))
        Config.Values.lastWatchedChannelsIds.sortBy { it.dateAdded }
        Config.Values.save(requireContext())

        playerFragment.startChannel(channel){
            if(requireContext().isTV)
                focusedView?.requestFocus()
        }
    }

    override fun onHorizontalFocusChanged(position: Int) {
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
                return SNAP_TO_START
            }
        }
    }

    private inner class SearchTextWatcher: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            recyclerChannelsAdapter.updateChannels(
                playlistViewModel.getChannels()!!.getChannelsForName(s.toString().trim())
            )
            binding.recyclerView.scrollToPosition(0)
        }
    }

    override fun tag(): String {
        return TAG
    }
}