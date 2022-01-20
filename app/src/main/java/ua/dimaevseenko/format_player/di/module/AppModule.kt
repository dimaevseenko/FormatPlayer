package ua.dimaevseenko.format_player.di.module

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides

@Module(includes = [PlayerModule::class])
object AppModule

@Module
object PlayerModule{

    @Provides
    fun providePlayer(context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }
}