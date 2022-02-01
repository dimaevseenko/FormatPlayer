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

abstract class StreamControlsFragment: Fragment() {

    companion object{
        const val TAG = "StreamControlsFragment"
    }

    private lateinit var stream: Stream

    private var timer: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stream = arguments?.getParcelable("stream")!!
    }

    internal fun getStream() = stream

    private fun startTimer(){
        timer = CoroutineScope(Dispatchers.Default).launch {
            delay(4000)
            launch(Dispatchers.Main) { dismiss() }
        }
    }

    override fun onResume() {
        startTimer()
        super.onResume()
    }

    override fun onPause() {
        timer?.cancel()
        super.onPause()
    }
    
    override fun onDestroy() {
        timer?.cancel()
        timer = null
        super.onDestroy()
    }

    internal fun dismiss(){
        (parentFragment as StreamFragment).dismissControls()
    }

    internal fun endStream(){
        (parentFragment as StreamFragment).onBackPressed()
    }
}