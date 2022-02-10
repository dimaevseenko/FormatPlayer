package ua.dimaevseenko.format_player.fragment.player.stream

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.model.Stream
import javax.inject.Inject

abstract class StreamFragment: Fragment(), SwipeHelper.Listener {

    companion object{
        const val TAG = "StreamFragment"
    }

    @Inject lateinit var swipeHelperFactory: SwipeHelper.Factory
    private lateinit var swipeHelper: SwipeHelper

    @Inject lateinit var streamPlayerFactory: StreamPlayer.Factory
    private lateinit var streamPlayer: StreamPlayer

    private lateinit var stream: Stream

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        swipeHelper = swipeHelperFactory.createSwipeHelper(
            getContentView(), getBackgroundView()
        ).apply { setSwipeListener(this@StreamFragment) }

        stream = arguments?.getParcelable("stream")!!

        if(savedInstanceState == null)
            swipeHelper.start(requireActivity().windowManager.defaultDisplay.height.toFloat())

        getStreamContainer().setOnTouchListener(swipeHelper)
        getStreamContainer().setOnClickListener { streamControls() }

        streamPlayer = streamPlayerFactory.createStreamPlayer(getPlayerView(), stream.getStreamUrl())

        if(savedInstanceState == null)
            streamControls()
    }

    internal abstract fun getStreamContainer(): View

    internal abstract fun getContentView(): View

    internal abstract fun getBackgroundView(): View

    internal abstract fun getPlayerView(): PlayerView

    internal abstract fun getControlsFragment(): ControlsFragment

    fun getStream() = stream

    fun streamControls(){
        val controlsFragment = getFragment<ControlsFragment>(ControlsFragment.TAG)
        if(controlsFragment == null)
            replaceFragment(R.id.streamContainer, getControlsFragment(), ControlsFragment.TAG, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        else {
            removeFragment(controlsFragment, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            requestFocus()
        }
    }

    private fun requestFocus(){
        if(requireContext().isTV)
            getStreamContainer().requestFocus()
    }

    override fun onSwiped(close: Boolean) {
        if(close)
            if(isAdded)
                playerFragment.dismissStream(this)
    }

    open fun onBackPressed(): Boolean{
        swipeHelper.close()
        return true
    }

    override fun onResume() {
        streamPlayer.start()
        super.onResume()
    }

    override fun onPause() {
        streamPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        streamPlayer.stop()
        super.onDestroy()
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent?){
        getFragment<ControlsFragment>(ControlsFragment.TAG)?.onKeyDown(keyCode, event)
    }
}