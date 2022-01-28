package ua.dimaevseenko.format_player.fragment.player.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.DefaultAudioSink
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentStreamBinding
import ua.dimaevseenko.format_player.fragment.player.AnimatedFragment
import ua.dimaevseenko.format_player.model.Stream
import javax.inject.Inject

class StreamFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "StreamFragment"
    }

    private lateinit var binding: FragmentStreamBinding

    @Inject lateinit var streamPlayerFactory: StreamPlayer.Factory
    private lateinit var streamPlayer: StreamPlayer

    @Inject lateinit var streamControlsFragment: StreamControlsFragment

    private lateinit var stream: Stream

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamBinding.bind(inflater.inflate(R.layout.fragment_stream, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartY(duration = 400){
                if(requireContext().isTV)
                    binding.streamContainer.requestFocus()
            }

        stream = arguments?.getParcelable("stream")!!
        streamPlayer = streamPlayerFactory.createStreamPlayer(
            binding.playerView,
            stream.getStreamUrl()
        )
        streamPlayer.start()

        binding.streamContainer.setOnClickListener { streamControls() }
    }

    private fun streamControls(){
        if(getFragment<StreamControlsFragment>(StreamControlsFragment.TAG) == null)
            addFragment(R.id.streamContainer, streamControlsFragment.apply {
                arguments = Bundle().apply { putParcelable("stream", stream) }
            }, StreamControlsFragment.TAG, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }

    fun dismissControls(){
        removeFragment(getFragment<StreamControlsFragment>(StreamControlsFragment.TAG)!!, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        if(requireContext().isTV)
            binding.streamContainer.requestFocus()
    }

    override fun onPause() {
        streamPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        streamPlayer.stop()
        super.onDestroy()
    }

    override fun onResume() {
        streamPlayer.start()
        super.onResume()
    }

    private fun dismiss(){
        animateEndY(duration = 400) {
            parentFragment?.removeFragment(this, true)
        }
    }

    fun onBackPressed(): Boolean{
        dismiss()
        return true
    }
}