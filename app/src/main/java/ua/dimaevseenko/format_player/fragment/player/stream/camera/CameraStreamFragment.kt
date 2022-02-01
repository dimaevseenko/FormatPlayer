package ua.dimaevseenko.format_player.fragment.player.stream.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamCameraBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamControlsFragment
import ua.dimaevseenko.format_player.getFragment
import javax.inject.Inject

class CameraStreamFragment @Inject constructor(): StreamFragment() {

    @Inject lateinit var streamControls: CameraStreamControlsFragment

    private lateinit var binding: FragmentStreamCameraBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamCameraBinding.bind(inflater.inflate(R.layout.fragment_stream_camera, container, false))
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
        return streamControls
    }

    override fun getStreamContainer(): FrameLayout {
        return binding.streamContainer
    }
}