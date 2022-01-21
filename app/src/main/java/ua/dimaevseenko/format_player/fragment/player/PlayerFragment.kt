package ua.dimaevseenko.format_player.fragment.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentPlayerBinding
import ua.dimaevseenko.format_player.fragment.MainFragment
import javax.inject.Inject

class PlayerFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "PlayerFragment"
    }

    private lateinit var binding: FragmentPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        binding.button.setOnClickListener {
            Config.Values.login = null
            Config.Values.mToken = null
            Config.Values.save(requireContext())
            (parentFragment as MainFragment).authFragment()
        }
    }
}