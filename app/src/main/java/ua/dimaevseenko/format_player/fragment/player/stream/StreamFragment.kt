package ua.dimaevseenko.format_player.fragment.player.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentStreamBinding
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Stream
import ua.dimaevseenko.format_player.removeFragment
import javax.inject.Inject

class StreamFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "StreamFragment"
    }

    private lateinit var binding: FragmentStreamBinding

    @Inject lateinit var player: ExoPlayer
    @Inject lateinit var dataSourceFactory: DefaultDataSource.Factory

    private lateinit var stream: Stream

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamBinding.bind(inflater.inflate(R.layout.fragment_stream, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        stream = arguments?.getParcelable("stream")!!
        startPlayer()
    }

    private fun startPlayer(){
        binding.playerView.player = player
        player.setMediaSource(createMediaSource())
        player.prepare()
        player.play()
    }

    override fun onPause() {
        player.pause()
        super.onPause()
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }

    override fun onResume() {
        player.prepare()
        player.play()
        super.onResume()
    }

    private fun createMediaSource(): HlsMediaSource{
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(stream.getStreamUrl())
        )
    }

    fun onBackPressed(): Boolean{
        parentFragment?.removeFragment(this, true)
        return true
    }
}