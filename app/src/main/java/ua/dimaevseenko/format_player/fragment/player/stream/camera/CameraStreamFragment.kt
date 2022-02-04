package ua.dimaevseenko.format_player.fragment.player.stream.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamCameraBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import javax.inject.Inject

class CameraStreamFragment @Inject constructor(): StreamFragment() {

    private lateinit var binding: FragmentStreamCameraBinding

    @Inject lateinit var controlsFragment: CameraControlsFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamCameraBinding.bind(inflater.inflate(R.layout.fragment_stream_camera, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
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
}