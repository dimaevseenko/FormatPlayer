package ua.dimaevseenko.format_player.fragment.player.stream.channel.settings

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SettingsSwipeHelper @AssistedInject constructor(
    @Assisted("rootView")
    private val rootView: View
): View.OnTouchListener {

    private var initY = 0f

    private var listener: Listener? = null

    fun setListener(listener: Listener){
        this.listener = listener
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> { return actionDown(event) }
            MotionEvent.ACTION_MOVE -> { return actionMove(event) }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> { return actionCancel(event) }
        }
        return false
    }

    private fun actionDown(event: MotionEvent): Boolean{
        initY = event.rawY
        return false
    }

    private fun actionMove(event: MotionEvent): Boolean{
        val dY = event.rawY - initY
        if(dY>0)
            rootView.translationY = dY
        else
            rootView.translationY = 0f
        return true
    }

    private fun actionCancel(event: MotionEvent): Boolean{
        if(event.rawY <= rootView.rootView.height/1.1)
            animateY(rootView.translationY, 0f){
                listener?.onSwiped(false)
            }
        else
            animateY(rootView.translationY, rootView.height.toFloat()){
                listener?.onSwiped(true)
            }
        return true
    }

    fun dismiss(completion: (() -> Unit)?){
        animateY(rootView.translationY, rootView.height.toFloat(), completion)
    }

    interface Listener{
        fun onSwiped(close: Boolean)
    }

    private fun animateY(fromY: Float, toY: Float, completion: (()->Unit)? = null){
        ObjectAnimator.ofFloat(rootView, View.TRANSLATION_Y, fromY, toY).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 500
            addListener({
                completion?.let { it() }
            })
            start()
        }
    }

    @AssistedFactory
    interface Factory{
        fun createSwipeHelper(
            @Assisted("rootView")
            rootView: View
        ): SettingsSwipeHelper
    }
}