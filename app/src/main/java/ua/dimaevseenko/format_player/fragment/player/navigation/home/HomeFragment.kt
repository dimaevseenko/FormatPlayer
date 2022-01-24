package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentHomeBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.NavFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.PlayerNavFragment
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class HomeFragment @Inject constructor(): NavFragment() {

    companion object{
        const val TAG = "HomeFragment"
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.channelsLayout.setOnClickListener { (parentFragment as PlayerNavFragment).addVideoFragment() }

        if(requireContext().isTV)
            binding.channelsLayout.requestFocus()
    }

    override fun tag(): String {
        return TAG
    }

    override fun animatedFragment(): Boolean {
        return true
    }
}