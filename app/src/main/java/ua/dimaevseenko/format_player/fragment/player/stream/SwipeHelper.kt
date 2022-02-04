package ua.dimaevseenko.format_player.fragment.player.stream

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SwipeHelper @AssistedInject constructor(
    @Assisted("contentView")
    private val contentView: View,
    @Assisted("backgroundView")
    private val backgroundView: View
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
            contentView.translationY = dY
        else
            contentView.translationY = 0f

        alphaBackground(contentView.translationY)
        return true
    }

    private fun actionCancel(): Boolean{
        if(contentView.translationY <= contentView.height.toFloat()/1.5)
            animateY(contentView.translationY, 0f, false)
        else
            animateY(contentView.translationY, contentView.height.toFloat(), true)

        return contentView.translationY != 0f
    }

    private fun alphaBackground(animatedValue: Float){
        backgroundView.alpha = ((animatedValue/contentView.height.toFloat()-1)*-1)
    }

    fun setSwipeListener(listener: Listener){
        this.listener = listener
    }

    interface Listener{
        fun onSwiped(close: Boolean)
    }

    fun start(fromY: Float){
        animateY(fromY, 0f, false)
    }

    fun close(){
        animateY(contentView.translationY, contentView.height.toFloat(), true)
    }

    private fun animateY(fromY: Float, toY: Float, close: Boolean){
        ObjectAnimator.ofFloat(contentView, View.TRANSLATION_Y, fromY, toY).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 500
            addUpdateListener { alphaBackground(animatedValue as Float) }
            addListener({
                listener?.onSwiped(close)
            })
        }.start()
    }

    @AssistedFactory
    interface Factory{
        fun createSwipeHelper(
            @Assisted("contentView")
            contentView: View,
            @Assisted("backgroundView")
            backgroundView: View
        ): SwipeHelper
    }
}