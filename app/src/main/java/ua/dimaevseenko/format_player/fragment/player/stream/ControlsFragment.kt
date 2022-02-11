package ua.dimaevseenko.format_player.fragment.player.stream

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import ua.dimaevseenko.format_player.getFragment
import ua.dimaevseenko.format_player.model.Stream

open class ControlsFragment: Fragment() {

    companion object{
        const val TAG = "ControlsFragment"
    }

    private var timer: Job? = null
    private var isFullscreen = false

    internal fun startTimer(){
        timer = CoroutineScope(Dispatchers.Default).launch {
            delay(4000)
            launch(Dispatchers.Main) { dismiss() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isFullscreen = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
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
        stopTimer()
        super.onDestroy()
    }

    internal fun dismissStream(){
        (parentFragment as StreamFragment).onBackPressed()
    }

    private fun dismiss(){
        (parentFragment as StreamFragment).streamControls()
    }

    fun getStream(): Stream{
        return (parentFragment as StreamFragment).getStream()
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?){
        stopTimer()
        startTimer()
    }

    internal fun fullscreen(){
        if(!isFullscreen)
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        isFullscreen = !isFullscreen
    }

    internal fun stopTimer(){
        timer?.cancel()
        timer = null
    }
}