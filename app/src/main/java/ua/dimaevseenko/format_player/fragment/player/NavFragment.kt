package ua.dimaevseenko.format_player.fragment.player

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

abstract class NavFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(animatedFragment() && savedInstanceState == null)
            animateY(view, requireActivity().windowManager.defaultDisplay.height.toFloat(), 0f)
    }

    abstract fun animatedFragment(): Boolean
    abstract fun tag(): String

    override fun onPause() {
        if(animatedFragment())
            view?.let { animateY(it, 0f, -it.height.toFloat()) }
        super.onPause()
    }

    private fun animateY(view: View, fromY: Float, toY: Float){
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, fromY, toY).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 500
            start()
        }
    }
}