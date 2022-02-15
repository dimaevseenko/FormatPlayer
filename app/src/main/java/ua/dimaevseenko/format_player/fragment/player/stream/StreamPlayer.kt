package ua.dimaevseenko.format_player.fragment.player.stream

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.model.Quality
import ua.dimaevseenko.format_player.model.Stream

class StreamPlayer @AssistedInject constructor(
    @Assisted("playerView")
    private val playerView: PlayerView,
    @Assisted("stream")
    private var stream: Stream,
    private val player: ExoPlayer,
    private val dataSourceFactory: DefaultDataSource.Factory,
    private val context: Context
) {

    init{
        playerView.player = player
        player.setMediaSource(createMediaSource())
    }

    private fun createMediaSource(): HlsMediaSource {
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(stream.getStreamUrl())
        )
    }

    fun setQuality(quality: Quality){
        val bitrate = when(quality){
            Quality.LOW -> 208000
            Quality.MID -> 960000
            Quality.SD -> 2192000
            Quality.HD -> 2692000
            Quality.FULL_HD -> 4192000
            Quality.AUTO -> 0
        }

        setQualityBitrate(bitrate)
    }

    fun getQuality(): Quality{
        getVideoFormat()?.let {
            return when(it.bitrate){
                208000 -> Quality.LOW
                960000 -> Quality.MID
                2192000 -> Quality.SD
                2692000 -> Quality.HD
                4192000 -> Quality.FULL_HD
                else -> Quality.AUTO
            }
        }
        return Quality.AUTO
    }

    fun getMaxBitrate(): Int?{
        return player.trackSelector?.parameters?.maxVideoBitrate
    }

    fun setQualityBitrate(bitrate: Int){
        if(bitrate == 0) {
            player.trackSelector?.parameters =
                DefaultTrackSelector(context).buildUponParameters().build()
            return
        }

        player.trackSelector?.parameters = DefaultTrackSelector(context).buildUponParameters().setMaxVideoBitrate(
            bitrate
        ).build()
    }

    fun getVideoFormat(): Format?{
        return player.videoFormat
    }

    fun updateStream(stream: Stream){
        this.stream = stream
        player.setMediaSource(createMediaSource())
    }

    fun getPlayerPosition(): Long{
        return player.currentPosition
    }

    fun setPosition(position: Long){
        player.seekTo(position)
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

    fun release(){
        player.release()
    }

    @AssistedFactory
    interface Factory{
        fun createStreamPlayer(
            @Assisted("playerView")
            playerView: PlayerView,
            @Assisted("stream")
            stream: Stream
        ): StreamPlayer
    }
}