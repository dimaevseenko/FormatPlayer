package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentChannelsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import javax.inject.Inject

class ChannelsFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "ChannelsFragment"
    }

    private lateinit var binding: FragmentChannelsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChannelsBinding.bind(inflater.inflate(R.layout.fragment_channels, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
            animateStartX()

        binding.button2.setOnClickListener { dismiss() }
    }

    fun dismiss(){
        animateEndX()
        (parentFragment as HomeFragment).homeWaysFragment()
    }
}