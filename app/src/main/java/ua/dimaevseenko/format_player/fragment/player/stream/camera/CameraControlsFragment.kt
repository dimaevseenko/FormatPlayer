package ua.dimaevseenko.format_player.fragment.player.stream.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsCameraBinding
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class CameraControlsFragment @Inject constructor(): ControlsFragment() {

    private lateinit var binding: FragmentStreamControlsCameraBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsCameraBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_camera, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()

        binding.fullscreenImageButton.setOnClickListener { fullscreen() }
        binding.titleTextView.text = getStream().getStreamTitle()
        binding.hidePlayerImageButton.setOnClickListener { dismissStream() }
        binding.nameTextView.text = "FORMAT MARIUPOL"
    }
}