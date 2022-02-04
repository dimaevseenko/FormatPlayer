package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ui.PlayerView
import dagger.Lazy
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.model.Programs
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelStreamFragment @Inject constructor(): StreamFragment(), Server.Listener<ProgramsResult> {

    private lateinit var binding: FragmentStreamChannelBinding

    @Inject lateinit var programsViewModelFactory: ProgramsViewModel.Factory
    private lateinit var programsViewModel: ProgramsViewModel

    @Inject lateinit var recyclerAdapterFactory: ChannelProgramsRecyclerAdapter.Factory
    private lateinit var recyclerAdapter: ChannelProgramsRecyclerAdapter

    @Inject lateinit var controlsFragment: ChannelControlsFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        programsViewModel = ViewModelProvider(requireActivity(), programsViewModelFactory).get(ProgramsViewModel::class.java)
        programsViewModel.addListener(TAG, this)
        programsViewModel.getPrograms(getStream().getStreamId())
    }

    override fun onResponse(result: ProgramsResult) {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            loadRecycler(result.requirePrograms())
    }

    override fun onFailure(t: Throwable) {

    }

    private fun loadRecycler(programs: Programs){
        recyclerAdapter = recyclerAdapterFactory.createChannelProgramsRecyclerAdapter(programs)

        binding.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView?.adapter = recyclerAdapter
        binding.recyclerView?.scrollToPosition(programs.getCurrentProgramPosition())
    }

    override fun getControlsFragment(): ControlsFragment {
        return controlsFragment
    }

    override fun getStreamContainer(): View {
        return binding.streamContainer
    }

    override fun getContentView(): View {
        return binding.contentLayout
    }

    override fun getBackgroundView(): View {
        return binding.backgroundLayout
    }

    override fun getPlayerView(): PlayerView {
        return binding.playerView
    }

    override fun onDestroy() {
        programsViewModel.removeListener(TAG)
        super.onDestroy()
    }
}