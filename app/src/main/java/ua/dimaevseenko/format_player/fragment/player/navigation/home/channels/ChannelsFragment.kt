package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentChannelsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.model.Genre
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class ChannelsFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "ChannelsFragment"
    }

    private lateinit var binding: FragmentChannelsBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var verticalChannelsAdapterFactory: VerticalChannelsAdapter.Factory
    @Inject lateinit var horizontalChannelsAdapterFactory: HorizontalChannelsAdapter.Factory

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

        binding.backCard.setOnClickListener { dismiss() }
        loadRecycler()
    }

    private fun loadRecycler(){
        binding.recyclerView.layoutManager = getLinearLayoutManager()
        binding.recyclerView.adapter = getRecyclerAdapter()
    }

    private fun getLinearLayoutManager(): LinearLayoutManager{
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(requireContext())
        else
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getRecyclerAdapter(): RecyclerChannelsAdapter{
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            verticalChannelsAdapterFactory.createVerticalChannelsAdapter(playlistViewModel.getChannels()!!)
        else
            horizontalChannelsAdapterFactory.createHorizontalChannelsAdapter(playlistViewModel.getChannels()!!)
    }

    private fun loadGenres(){
        binding.channelsGenresTabLayout.addTab(createTab(Genre("0", getString(R.string.all))))
        playlistViewModel.getGenres()?.forEach {
            binding.channelsGenresTabLayout.addTab(createTab(it))
        }
    }

    private fun createTab(genre: Genre): TabLayout.Tab{
        return binding.channelsGenresTabLayout.newTab().apply {
            this.text = genre.name
            this.id = genre.id.toInt()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            binding.channelsGenresTabLayout.getTabAt(it.getInt("genrePosition"))?.select()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("genrePosition", binding.channelsGenresTabLayout.selectedTabPosition)
    }

    fun dismiss(){
        animateEndX()
        (parentFragment as HomeFragment).homeWaysFragment()
    }
}