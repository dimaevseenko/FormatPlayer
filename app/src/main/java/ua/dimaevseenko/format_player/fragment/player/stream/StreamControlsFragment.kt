package ua.dimaevseenko.format_player.fragment.player.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.*
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsBinding
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Stream
import ua.dimaevseenko.format_player.removeFragment
import javax.inject.Inject

class StreamControlsFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "StreamControlsFragment"
    }

    private lateinit var binding: FragmentStreamControlsBinding

    private lateinit var stream: Stream

    private var timer: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsBinding.bind(inflater.inflate(R.layout.fragment_stream_controls, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stream = arguments?.getParcelable("stream")!!
        binding.root.setOnClickListener { dismiss() }
        binding.titleTextView.text = stream.getStreamTitle()
        binding.hidePlayerImageButton.setOnClickListener { (parentFragment as StreamFragment).onBackPressed() }

        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()

        startTimer()
    }

    private fun startTimer(){
        timer = CoroutineScope(Dispatchers.Default).launch {
            delay(4000)
            launch(Dispatchers.Main) { dismiss() }
        }
    }

    override fun onDestroy() {
        timer?.cancel()
        timer = null
        super.onDestroy()
    }

    private fun dismiss(){
        (parentFragment as StreamFragment).dismissControls()
    }
}