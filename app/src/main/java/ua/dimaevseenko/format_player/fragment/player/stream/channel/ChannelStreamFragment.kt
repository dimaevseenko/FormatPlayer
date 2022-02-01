package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import javax.inject.Inject

class ChannelStreamFragment @Inject constructor(): StreamFragment() {

    @Inject lateinit var channelStreamControlsFragment: ChannelStreamControlsFragment

    private lateinit var binding: FragmentStreamBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentStreamBinding.bind(inflater.inflate(R.layout.fragment_stream, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
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
}