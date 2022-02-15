package ua.dimaevseenko.format_player.fragment.player.stream.channel.catchup

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentCatchupBinding
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamFragment
import ua.dimaevseenko.format_player.model.Catchup
import ua.dimaevseenko.format_player.removeFragment
import javax.inject.Inject

class CatchupFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "CatchupFragment"
    }

    private lateinit var binding: FragmentCatchupBinding

    private lateinit var catchup: Catchup

    @Inject lateinit var player: ExoPlayer
    @Inject lateinit var dataSource: DefaultDataSource.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCatchupBinding.bind(inflater.inflate(R.layout.fragment_catchup, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        catchup = arguments?.getParcelable("catchup")!!

        player.setMediaSource(HlsMediaSource.Factory(dataSource).createMediaSource(MediaItem.fromUri(catchup.getStreamUrl())))

        binding.player.player = player

        player.prepare()
        player.play()

        Log.d("CHANELL", catchup.toString())
        Log.d("CHANELL", catchup.getStreamUrl())
        Log.d("CHANELL", ((catchup.getProgram().gmtTimeTo-catchup.getProgram().gmtTime)/60).toString())
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }

    fun onBackPressed(): Boolean{
        parentFragment?.removeFragment(this, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        (parentFragment as ChannelStreamFragment).getStreamContainer().requestFocus()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("position", player.currentPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            player.seekTo(it.getLong("position"))
        }
    }
}