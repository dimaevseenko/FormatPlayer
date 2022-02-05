package ua.dimaevseenko.format_player.di.module

import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class AnimationModule {

    @FocusAnimation
    @Provides
    fun provideFocusAnimation(): ScaleAnimation{
        return ScaleAnimation(
            1f,
            1.10f,
            1f,
            1.10f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            fillAfter = true
            duration = 150
        }
    }

    @UnFocusAnimation
    @Provides
    fun provideUnFocusAnimation(): ScaleAnimation{
        return ScaleAnimation(
            1.10f,
            1f,
            1.10f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            fillAfter = true
            duration = 150
        }
    }
}

@Qualifier
annotation class UnFocusAnimation

@Qualifier
annotation class FocusAnimation