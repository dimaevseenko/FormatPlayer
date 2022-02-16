package ua.dimaevseenko.format_player.fragment

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PresentationPlayer @AssistedInject constructor(
    @Assisted("view")
    private val playerView: PlayerView,
    @Assisted("repeat")
    private val repeat: Boolean,
    @Assisted("name")
    private val name: String,
    private val player: ExoPlayer,
    @Assisted("completion")
    private var completion: (()->Unit)?
): Player.Listener {

    init {
        player.setMediaItem(MediaItem.fromUri(Uri.parse("asset:///$name")))
        if(repeat)
            player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        playerView.player = player
        player.addListener(this)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if(playbackState == Player.STATE_ENDED)
            completion?.let { it() }
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

    fun release(){
        player.release()
    }

    val isPlaying: Boolean
        get() = player.isPlaying

    @AssistedFactory
    interface Factory{
        fun createPresentationPlayer(
            @Assisted("view") playerView: PlayerView,
            @Assisted("repeat") repeat: Boolean,
            @Assisted("name") name: String,
            @Assisted("completion") completion: (() -> Unit)? = null
        ): PresentationPlayer
    }
}