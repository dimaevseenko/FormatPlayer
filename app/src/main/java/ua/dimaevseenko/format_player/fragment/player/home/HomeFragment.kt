package ua.dimaevseenko.format_player.fragment.player.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentHomeBinding
import ua.dimaevseenko.format_player.fragment.player.NavFragment
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
    }

    override fun tag(): String {
        return TAG
    }

    override fun animatedFragment(): Boolean {
        return true
    }
}