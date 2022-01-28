package ua.dimaevseenko.format_player.fragment.player.stream

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class StreamPlayer @AssistedInject constructor(
    @Assisted("playerView")
    private val playerView: PlayerView,
    @Assisted("streamUrl")
    private val streamUrl: String,
    private val player: ExoPlayer,
    private val dataSourceFactory: DefaultDataSource.Factory
) {

    init{
        playerView.player = player
        player.setMediaSource(createMediaSource())
    }

    private fun createMediaSource(): HlsMediaSource {
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(streamUrl)
        )
    }

    fun start(){
        player.prepare()
        player.play()
    }

    fun pause(){
        player.pause()
    }

    fun stop(){
        player.stop()
    }

    @AssistedFactory
    interface Factory{
        fun createStreamPlayer(
            @Assisted("playerView")
            playerView: PlayerView,
            @Assisted("streamUrl")
            streamUrl: String
        ): StreamPlayer
    }
}