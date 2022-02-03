package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.getFragment
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import ua.dimaevseenko.format_player.viewmodel.RequestViewModel
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChannelStreamFragment @Inject constructor(): StreamFragment(), Server.Listener<ProgramsResult> {

    @Inject lateinit var channelStreamControlsFragment: ChannelStreamControlsFragment

    private lateinit var binding: FragmentStreamChannelBinding

    @Inject lateinit var channelProgramsRecyclerAdapter: ChannelProgramsRecyclerAdapter.Factory

    @Inject lateinit var programsViewModelFactory: ProgramsViewModel.Factory
    private lateinit var programsViewModel: ProgramsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentStreamChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)

        programsViewModel = ViewModelProvider(viewModelStore, programsViewModelFactory).get(ProgramsViewModel::class.java)
        programsViewModel.listener = this
        programsViewModel.getPrograms(getStream().getStreamId())
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun getPlayerView(): PlayerView {
        return binding.playerView
    }

    override fun getStreamControls(): StreamControlsFragment {
        return channelStreamControlsFragment
    }

    override fun getStreamContainer(): FrameLayout {
        return binding.streamContainer
    }

    override fun onResponse(result: ProgramsResult) {
        binding.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView?.adapter = channelProgramsRecyclerAdapter.createChannelProgramsRecyclerAdapter(result.requirePrograms())
        binding.recyclerView?.scrollToPosition(result.requirePrograms().getCurrentProgramPosition())
    }

    override fun onFailure(t: Throwable) {

    }

    override fun onDestroy() {
        programsViewModel.listener = null
        super.onDestroy()
    }
}