package ua.dimaevseenko.format_player.fragment.main.auth

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PresentationPlayer @AssistedInject constructor(
    @Assisted("view")
    private val playerView: PlayerView,
    private val player: ExoPlayer
) {

    init {
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        player.setMediaItem(MediaItem.fromUri(Uri.parse("asset:///...")))
        playerView.player = player
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        playerView.useController = false
    }

    fun play(){
        player.prepare()
        player.play()
    }

    fun pause(){
        player.pause()
    }

    fun stop(){
        player.stop()
    }

    val isPlaying: Boolean
        get() = player.isPlaying

    @AssistedFactory
    interface Factory{
        fun createPresentationPlayer(
            @Assisted("view") playerView: PlayerView
        ): PresentationPlayer
    }
}