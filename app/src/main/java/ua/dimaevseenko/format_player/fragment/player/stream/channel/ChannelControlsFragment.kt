package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelControlsFragment @Inject constructor(): ControlsFragment() {

    private lateinit var binding: FragmentStreamControlsChannelBinding

    private lateinit var programsViewModel: ProgramsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        programsViewModel = ViewModelProvider(requireActivity()).get(ProgramsViewModel::class.java)
        initView()
    }

    private fun initView(){
        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()

        binding.titleTextView.text = getStream().getStreamTitle()
        binding.hidePlayerImageButton.setOnClickListener { dismissStream() }
        programsViewModel.getCurrentProgram(getStream().getStreamId())?.let { binding.nameTextView.text = it.name }
    }
}