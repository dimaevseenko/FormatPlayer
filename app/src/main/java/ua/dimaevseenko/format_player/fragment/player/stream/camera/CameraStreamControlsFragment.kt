package ua.dimaevseenko.format_player.fragment.player.stream.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsCameraBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import javax.inject.Inject

class CameraStreamControlsFragment @Inject constructor(): StreamControlsFragment() {

    private lateinit var binding: FragmentStreamControlsCameraBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsCameraBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_camera, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hidePlayerImageButton.setOnClickListener { endStream() }
        binding.titleTextView.text = getStream().getStreamTitle()
        binding.nameTextView.text = "camera"
    }
}