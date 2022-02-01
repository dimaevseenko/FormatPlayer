package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class ChannelStreamControlsFragment @Inject constructor(): StreamControlsFragment() {

    private lateinit var binding: FragmentStreamControlsChannelBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_camera, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(requireContext().isTV)
            requireFocus()

        binding.hidePlayerImageButton.setOnClickListener { endStream() }
        binding.titleTextView.text = getStream().getStreamTitle()
        binding.nameTextView.text = "channel"
    }

    private fun requireFocus() {
        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()
    }
}