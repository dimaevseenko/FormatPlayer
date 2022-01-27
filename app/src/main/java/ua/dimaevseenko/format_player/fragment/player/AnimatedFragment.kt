package ua.dimaevseenko.format_player.fragment.player

import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

open class AnimatedFragment: Fragment() {

    internal open fun tag(): String = "tag"

    internal var isAnimated = true

    internal fun animateEndX(reverse: Boolean = false, completion: (() -> Unit)? = null){
        var finalX = requireActivity().windowManager.defaultDisplay.width.toFloat()
        if(reverse)
            finalX *= -1

        animateX(requireView(), 0f, finalX, completion)
    }

    internal fun animateStartX(reverse: Boolean = false, completion: (() -> Unit)? = null){
        var finalX = requireActivity().windowManager.defaultDisplay.width.toFloat()
        if(reverse)
            finalX *= -1

        animateX(requireView(), finalX, 0f, completion)
    }

    internal fun animateEndY(reverse: Boolean = false, completion: (() -> Unit)? = null){
        var finalY = requireActivity().windowManager.defaultDisplay.height.toFloat()
        if(reverse)
            finalY *= -1

        animateY(requireView(), 0f, finalY, completion)
    }

    internal fun animateStartY(reverse: Boolean = false, completion: (() -> Unit)? = null){
        var finalY = requireActivity().windowManager.defaultDisplay.height.toFloat()
        if(reverse)
            finalY *= -1

        animateY(requireView(), finalY, 0f, completion)
    }

    private fun animateX(view: View, fromX: Float, toX: Float, completion: (() -> Unit)? = null){
        isAnimated = false
        animateXY(view, View.TRANSLATION_X, fromX, toX, completion)
    }

    private fun animateY(view: View, fromY: Float, toY: Float, completion: (() -> Unit)? = null){
        isAnimated = false
        animateXY(view, View.TRANSLATION_Y, fromY, toY, completion)
    }

    private fun animateXY(view: View, type: Property<View, Float>, from: Float, to: Float, completion: (()-> Unit)? = null){
        ObjectAnimator.ofFloat(view, type, from, to).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 500
            addListener({
                isAnimated = true
                completion?.let { it() }
            })
            start()
        }
    }
}