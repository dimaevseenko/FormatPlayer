package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentChannelsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class ChannelsFragment @Inject constructor(): AnimatedFragment(), Server.Listener<PlaylistResult> {

    companion object{
        const val TAG = "ChannelsFragment"
    }

    private lateinit var binding: FragmentChannelsBinding

    @Inject lateinit var playlistViewModelFactory: PlaylistViewModel.Factory
    private lateinit var playlistViewModel: PlaylistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChannelsBinding.bind(inflater.inflate(R.layout.fragment_channels, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        if(savedInstanceState == null)
            animateStartX()

        playlistViewModel = ViewModelProvider(requireActivity(), playlistViewModelFactory).get(PlaylistViewModel::class.java)
        playlistViewModel.listener = this
        playlistViewModel.getGenres()

        binding.backCard.setOnClickListener { dismiss() }
    }

    override fun onResponse(result: PlaylistResult) {
        binding.channelsGenresTabLayout.addTab(binding.channelsGenresTabLayout.newTab().apply {
            text = "Все"
            id = 0
        })

        result.genres.forEach {
            binding.channelsGenresTabLayout.addTab(binding.channelsGenresTabLayout.newTab().apply {
                text = it.name
                id = it.id.toInt()
            })
        }
    }

    override fun onFailure(t: Throwable) {

    }

    fun dismiss(){
        animateEndX()
        (parentFragment as HomeFragment).homeWaysFragment()
    }
}