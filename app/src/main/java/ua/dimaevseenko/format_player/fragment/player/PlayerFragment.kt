package ua.dimaevseenko.format_player.fragment.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.Lazy
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentPlayerBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.PlayerNavFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.camera.CameraStreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamFragment
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.model.Stream
import javax.inject.Inject

class PlayerFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "PlayerFragment"
    }

    private lateinit var binding: FragmentPlayerBinding

    @Inject lateinit var playerNavFragment: PlayerNavFragment

    @Inject lateinit var channelStreamFragment: Lazy<ChannelStreamFragment>
    @Inject lateinit var cameraStreamFragment: Lazy<CameraStreamFragment>

    private var lastFocusedView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.playerContainer, playerNavFragment, PlayerNavFragment.TAG, true)
    }

    fun startStream(stream: Stream, lastFocusedView: View? = null){
        this.lastFocusedView = lastFocusedView
        val streamFragment = if(stream is Channel) channelStreamFragment.get() else cameraStreamFragment.get()
        streamFragment.arguments = Bundle().apply { putParcelable("stream", stream) }

        if(getFragment<StreamFragment>(StreamFragment.TAG) == null)
            addFragment(R.id.playerContainer, streamFragment, StreamFragment.TAG, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }

    fun requireLastFocus(){
        if(requireContext().isTV)
            lastFocusedView?.requestFocus()
    }

    fun onBackPressed(): Boolean{
        getFragment<StreamFragment>(StreamFragment.TAG)?.let { return it.onBackPressed() }
        getFragment<PlayerNavFragment>(PlayerNavFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }
}