package ua.dimaevseenko.format_player.base

import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

open class AnimatedFragment: BaseFragment() {
    internal var isAnimated = true

    internal fun animateEndX(reverse: Boolean = false, duration: Long = 500, completion: (() -> Unit)? = null){
        var finalX = requireActivity().windowManager.defaultDisplay.width.toFloat()
        if(reverse)
            finalX *= -1

        animateX(requireView(), 0f, finalX, completion, duration)
    }

    internal fun animateStartX(reverse: Boolean = false, duration: Long = 500, completion: (() -> Unit)? = null){
        var finalX = requireActivity().windowManager.defaultDisplay.width.toFloat()
        if(reverse)
            finalX *= -1

        animateX(requireView(), finalX, 0f, completion, duration)
    }

    internal fun animateEndY(reverse: Boolean = false, duration: Long = 500, completion: (() -> Unit)? = null){
        var finalY = requireActivity().windowManager.defaultDisplay.height.toFloat()
        if(reverse)
            finalY *= -1

        animateY(requireView(), 0f, finalY, completion, duration)
    }

    internal fun animateStartY(reverse: Boolean = false, duration: Long = 500, completion: (() -> Unit)? = null){
        var finalY = requireActivity().windowManager.defaultDisplay.height.toFloat()
        if(reverse)
            finalY *= -1

        animateY(requireView(), finalY, 0f, completion, duration)
    }

    private fun animateX(view: View, fromX: Float, toX: Float, completion: (() -> Unit)? = null, duration: Long){
        isAnimated = false
        animateXY(view, View.TRANSLATION_X, fromX, toX, completion, duration)
    }

    private fun animateY(view: View, fromY: Float, toY: Float, completion: (() -> Unit)? = null, duration: Long){
        isAnimated = false
        animateXY(view, View.TRANSLATION_Y, fromY, toY, completion, duration)
    }

    private fun animateXY(view: View, type: Property<View, Float>, from: Float, to: Float, completion: (()-> Unit)? = null, duration: Long){
        ObjectAnimator.ofFloat(view, type, from, to).apply {
            interpolator = FastOutSlowInInterpolator()
            this.duration = duration
            addListener({
                isAnimated = true
                completion?.let { it() }
            })
            start()
        }
    }
}