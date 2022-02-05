package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.content.Context
import android.view.View
import android.view.animation.ScaleAnimation
import ua.dimaevseenko.format_player.di.module.FocusAnimation
import ua.dimaevseenko.format_player.di.module.UnFocusAnimation
import javax.inject.Inject

class ChannelScaleAnimator @Inject constructor(){

    @FocusAnimation
    @Inject
    lateinit var focusAnimation: ScaleAnimation

    @UnFocusAnimation
    @Inject
    lateinit var unFocusAnimation: ScaleAnimation
}