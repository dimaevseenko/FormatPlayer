package ua.dimaevseenko.format_player.fragment.player.stream

import android.view.MotionEvent
import android.view.View
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SwipeHelper @AssistedInject constructor(
    @Assisted("rootView")
    private val rootView: View
): View.OnTouchListener {

    private var listener: Listener? = null

    private var initY = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> return actionDown(event.rawY)
            MotionEvent.ACTION_MOVE -> return actionMove(event.rawY)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> return actionCancel()
        }
        return false
    }

    private fun actionDown(rawY: Float): Boolean{
        initY = rawY
        return false
    }

    private fun actionMove(rawY: Float): Boolean{
        val dY = rawY - initY
        if(dY>0)
            rootView.translationY = dY
        else
            rootView.translationY = 0f
        return true
    }

    private fun actionCancel(): Boolean{
        if(rootView.translationY <= rootView.height.toFloat()/1.5)
            listener?.onSwipe(false)
        else
            listener?.onSwipe(true)

        return rootView.translationY != 0f
    }

    fun setSwipeListener(listener: Listener){
        this.listener = listener
    }

    interface Listener{
        fun onSwipe(close: Boolean)
    }

    @AssistedFactory
    interface Factory{
        fun createSwipeHelper(
            @Assisted("rootView")
            rootView: View
        ): SwipeHelper
    }
}