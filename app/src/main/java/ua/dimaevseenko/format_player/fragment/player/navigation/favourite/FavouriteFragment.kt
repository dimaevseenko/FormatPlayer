package ua.dimaevseenko.format_player.fragment.player.navigation.favourite

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentFavouriteBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.HorizontalChannelsAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.RecyclerChannelsAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.VerticalChannelsAdapter
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.playerFragment
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class FavouriteFragment @Inject constructor(): AnimatedFragment(), HorizontalChannelsAdapter.Listener {

    companion object{
        const val TAG = "FavouriteFragment"
    }

    private lateinit var binding: FragmentFavouriteBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var verticalChannelsAdapterFactory: VerticalChannelsAdapter.Factory
    @Inject lateinit var horizontalChannelsAdapterFactory: HorizontalChannelsAdapter.Factory
    private lateinit var recyclerChannelsAdapter: RecyclerChannelsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavouriteBinding.bind(inflater.inflate(R.layout.fragment_favourite, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        if(savedInstanceState == null)
            animateStartY()

        checkEmpty()
        initView()
    }

    private fun checkEmpty(){
        if(playlistViewModel.getChannels()!!.getFavourite().size > 0)
            binding.favouriteEmptyTextView.visibility = View.GONE
        else
            binding.favouriteEmptyTextView.visibility = View.VISIBLE
    }

    override fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?) {
        playerFragment.startChannel(channel){
            recyclerChannelsAdapter.updateChannels(playlistViewModel.getChannels()!!.getFavourite())
            checkEmpty()

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

    private fun initView(){
        recyclerChannelsAdapter = verticalChannelsAdapterFactory.createVerticalChannelsAdapter(playlistViewModel.getChannels()!!.getFavourite())
        recyclerChannelsAdapter.setListener(this)

        binding.recyclerView.layoutManager = getLayoutManager()
        binding.recyclerView.adapter = getRecyclerAdapter()
    }

    private fun getLayoutManager(): LinearLayoutManager{
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(requireContext())
        else
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getRecyclerAdapter(): RecyclerChannelsAdapter{
        recyclerChannelsAdapter = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            verticalChannelsAdapterFactory.createVerticalChannelsAdapter(playlistViewModel!!.getChannels()!!.getFavourite()).apply { setListener(this@FavouriteFragment) }
        else
            horizontalChannelsAdapterFactory.createHorizontalChannelsAdapter(playlistViewModel.getChannels()!!.getFavourite()).apply { setListener(this@FavouriteFragment) }
        return recyclerChannelsAdapter
    }

    override fun tag(): String {
        return TAG
    }
}