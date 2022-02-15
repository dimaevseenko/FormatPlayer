package ua.dimaevseenko.format_player.fragment.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
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

    private var completionStreamFragment: (()->Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.playerContainer, playerNavFragment, PlayerNavFragment.TAG, true)
    }

    fun startStream(stream: Stream, completion: ()->Unit) {
        completionStreamFragment = completion

        val streamFragment =
            if (stream is Channel)
                appComponent.createChannelStreamFragment()
            else
                appComponent.createCameraStreamFragment()

        streamFragment.arguments = Bundle().apply { putParcelable("stream", stream) }

        val existStreamFragment = getFragment<StreamFragment>(StreamFragment.TAG)

        if (existStreamFragment == null)
            addFragment(
                R.id.streamContainer,
                streamFragment,
                StreamFragment.TAG,
                true,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
        else
            existStreamFragment.onBackPressed()
    }

    fun dismissStream(streamFragment: StreamFragment){
        if(streamFragment.isAdded){
            completionStreamFragment?.let { it() }
            removeFragment(streamFragment, true, FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    fun onBackPressed(): Boolean{
        getFragment<StreamFragment>(StreamFragment.TAG)?.let { return it.onBackPressed() }
        getFragment<PlayerNavFragment>(PlayerNavFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent?){
        getFragment<StreamFragment>(StreamFragment.TAG)?.onKeyDown(keyCode, event)
    }
}