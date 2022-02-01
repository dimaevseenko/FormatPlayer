package ua.dimaevseenko.format_player.fragment.player.stream

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.exoplayer2.ui.PlayerView
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.model.Stream
import javax.inject.Inject

abstract class StreamFragment: AnimatedFragment(), SwipeHelper.Listener {

    companion object{
        const val TAG = "StreamFragment"
    }

    @Inject lateinit var streamPlayerFactory: StreamPlayer.Factory
    private lateinit var streamPlayer: StreamPlayer

    @Inject lateinit var swipeHelperFactory: SwipeHelper.Factory

    private lateinit var stream: Stream

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if (savedInstanceState == null)
            animateStartY(duration = 400){
                streamControls()
            }

        stream = arguments?.getParcelable("stream")!!
        startPlayer()
        initStreamContainer()
    }

    abstract fun getRootView(): View

    abstract fun getPlayerView(): PlayerView

    abstract fun getStreamControls(): StreamControlsFragment

    abstract fun getStreamContainer(): FrameLayout

    @SuppressLint("ClickableViewAccessibility")
    private fun initStreamContainer(){
        getStreamContainer().setOnClickListener { streamControls() }
        getStreamContainer().setOnTouchListener(
            swipeHelperFactory.createSwipeHelper(getRootView()).apply {
                setSwipeListener(this@StreamFragment)
            }
        )
    }

    private fun startPlayer(){
        streamPlayer = streamPlayerFactory.createStreamPlayer(
            getPlayerView(),
            stream.getStreamUrl()
        )
        streamPlayer.start()
    }

    private fun streamControls(){
        if(getFragment<StreamControlsFragment>(StreamControlsFragment.TAG) == null)
            addFragment(
                getStreamContainer().id,
                getStreamControls().apply {
                    arguments = Bundle().apply { putParcelable("stream", stream) }
                },
                StreamControlsFragment.TAG,
                true,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
        else
            dismissControls()
    }

    fun dismissControls(){
        removeFragment(
            getFragment<StreamControlsFragment>(StreamControlsFragment.TAG)!!,
            true,
            FragmentTransaction.TRANSIT_FRAGMENT_FADE
        )

        if(requireContext().isTV)
            getStreamContainer().requestFocus()
    }

    override fun onSwipe(close: Boolean) {
        if(close)
            dismiss()
        else
            animateStartY(fromY = getRootView().translationY, duration = 400)
    }

    override fun onPause() {
        streamPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        streamPlayer.stop()
        super.onDestroy()
    }

    override fun onResume() {
        streamPlayer.start()
        super.onResume()
    }

    private fun dismiss(){
        animateEndY(duration = 400) {
            playerFragment.removeFragment(this, true)
        }
        playerFragment.requireLastFocus()
    }

    fun onBackPressed(): Boolean{
        if(isAnimated)
            dismiss()
        return true
    }
}