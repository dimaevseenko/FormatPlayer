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
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelControlsFragment @Inject constructor(): ControlsFragment(), Server.Listener<ProgramsResult> {

    private lateinit var binding: FragmentStreamControlsChannelBinding

    private lateinit var programsViewModel: ProgramsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        programsViewModel = ViewModelProvider(requireActivity()).get(ProgramsViewModel::class.java)
        programsViewModel.addListener(TAG, this)
        initView()
    }

    private fun initView(){
        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()

        binding.titleTextView.text = getStream().getStreamTitle()
        binding.hidePlayerImageButton.setOnClickListener { dismissStream() }
        programsViewModel.getCurrentProgram(getStream().getStreamId())?.let { binding.nameTextView.text = it.name }
    }

    override fun onResponse(result: ProgramsResult) {
        binding.nameTextView.text = result.requirePrograms().getCurrentProgram().name
    }

    override fun onFailure(t: Throwable) {}

    override fun onDestroy() {
        programsViewModel.removeListener(TAG)
        super.onDestroy()
    }
}