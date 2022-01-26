package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentChannelsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import ua.dimaevseenko.format_player.replaceFragment
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
        appComponent.inject(this)
        if(savedInstanceState == null)
            animateStartX()

        binding.backCard.setOnClickListener { dismiss() }
    }

    fun dismiss(){
        animateEndX()
        (parentFragment as HomeFragment).homeWaysFragment()
    }
}