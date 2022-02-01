package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsBinding
import ua.dimaevseenko.format_player.fragment.player.stream.StreamControlsFragment
import javax.inject.Inject

class ChannelStreamControlsFragment @Inject constructor(): StreamControlsFragment() {

    private lateinit var binding: FragmentStreamControlsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsBinding.bind(inflater.inflate(R.layout.fragment_stream_controls, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hidePlayerImageButton.setOnClickListener { endStream() }
        binding.titleTextView.text = getStream().getStreamTitle()
    }
}