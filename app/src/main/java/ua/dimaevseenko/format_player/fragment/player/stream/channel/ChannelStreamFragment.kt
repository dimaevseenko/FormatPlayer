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
import ua.dimaevseenko.format_player.viewmodel.RequestViewModel
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChannelStreamFragment @Inject constructor(): StreamFragment(), Server.Listener<ProgramsResult> {

    @Inject lateinit var channelStreamControlsFragment: ChannelStreamControlsFragment

    private lateinit var binding: FragmentStreamChannelBinding

    @Inject lateinit var requestViewModelFactory: RequestViewModel.Factory<ProgramsResult>
    private lateinit var requestViewModel: RequestViewModel<ProgramsResult>

    @Inject lateinit var channelProgramsRecyclerAdapter: ChannelProgramsRecyclerAdapter.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentStreamChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)

        requestViewModel = ViewModelProvider(viewModelStore, requestViewModelFactory).get(RequestViewModel::class.java) as RequestViewModel<ProgramsResult>
        requestViewModel.listener = this

        requestViewModel.request(
            Bundle().apply {
                putString("action", "getProgramsById")
                putString("id", getStream().getStreamId())
            }
        )
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
        result.programs?.sortBy { it.gmtTime }

        binding.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView?.adapter = channelProgramsRecyclerAdapter.createChannelProgramsRecyclerAdapter(result.requirePrograms())

        result.requirePrograms().forEach {
            program ->
            Log.d("PROGRAMS", program.getDay())
            Log.d("PROGRAMS", program.getTimeStart())
        }
    }

    override fun onFailure(t: Throwable) {

    }

    override fun onDestroy() {
        requestViewModel.listener = null
        super.onDestroy()
    }
}