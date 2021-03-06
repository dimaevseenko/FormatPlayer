package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.*
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentStreamChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelStreamFragment @Inject constructor(): StreamFragment(), Server.Listener<ProgramsResult> {

    private lateinit var binding: FragmentStreamChannelBinding

    @Inject lateinit var programsViewModelFactory: ProgramsViewModel.Factory
    private lateinit var programsViewModel: ProgramsViewModel

    @Inject lateinit var controlsFragment: ChannelControlsFragment
    @Inject lateinit var channelProgramsFragment: ChannelProgramsFragment

    private var jobUpdate: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        programsViewModel = ViewModelProvider(requireActivity(), programsViewModelFactory).get(ProgramsViewModel::class.java)
        programsViewModel.getPrograms(getStream().getStreamId())
        programsViewModel.addListener(TAG, this)

        binding.programsContainer?.let { replaceFragment(it.id, channelProgramsFragment, ChannelProgramsFragment.TAG, true) }
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

    override fun onResponse(result: ProgramsResult) {}

    override fun onFailure(t: Throwable) {
        jobUpdate?.cancel()
        jobUpdate = null
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            launch(Dispatchers.Main) { programsViewModel.getPrograms(getStream().getStreamId()) }
        }
    }

    override fun onDestroy() {
        jobUpdate?.cancel()
        jobUpdate = null
        programsViewModel.removeListener(TAG)
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        getFragment<ChannelControlsFragment>(ControlsFragment.TAG)?.let {
            if(it.onBackPressed())
                return true
        }
        return super.onBackPressed()
    }
}