package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.View
import android.view.animation.ScaleAnimation
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.di.module.FocusAnimation
import ua.dimaevseenko.format_player.di.module.UnFocusAnimation
import javax.inject.Inject

class FocusScaleAnimator @Inject constructor(
    private val context: Context
){

    fun animate(view: View, focus: Boolean){
        if(focus)
            view.startAnimation(context.appComponent.createFocusScaleAnimation())
        else
            view.startAnimation(context.appComponent.createUnFocusScaleAnimation())
    }
}