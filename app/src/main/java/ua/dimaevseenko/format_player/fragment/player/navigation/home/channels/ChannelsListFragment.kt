package ua.dimaevseenko.format_player.fragment.player.navigation.home.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentChennelsListBinding
import javax.inject.Inject

class ChannelsListFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "ChannelsListFragment"
    }

    private lateinit var binding: FragmentChennelsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChennelsListBinding.bind(inflater.inflate(R.layout.fragment_chennels_list, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            binding.button2.text = it.getString("genre")
        }
    }
}