package ua.dimaevseenko.format_player.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.addFragment
import ua.dimaevseenko.format_player.databinding.FragmentMainBinding
import ua.dimaevseenko.format_player.fragments.splash.SplashFragment
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding

    @Inject lateinit var splashFragment: SplashFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
            addFragment(R.id.mainFragmentContainer, splashFragment, SplashFragment.TAG, true)
    }
}