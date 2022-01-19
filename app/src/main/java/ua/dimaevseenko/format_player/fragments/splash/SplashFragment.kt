package ua.dimaevseenko.format_player.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentSplashBinding
import javax.inject.Inject

class SplashFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "SplashFragment"
    }

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false))
        return binding.root
    }
}