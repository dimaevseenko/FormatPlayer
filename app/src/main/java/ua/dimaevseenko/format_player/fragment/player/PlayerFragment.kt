package ua.dimaevseenko.format_player.fragment.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentPlayerBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.PlayerNavFragment
import javax.inject.Inject

class PlayerFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "PlayerFragment"
    }

    private lateinit var binding: FragmentPlayerBinding

    @Inject lateinit var playerNavFragment: PlayerNavFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.playerContainer, playerNavFragment, PlayerNavFragment.TAG, true)
    }

    fun onBackPressed(): Boolean{
        getFragment<PlayerNavFragment>(PlayerNavFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }
}