package ua.dimaevseenko.format_player.fragment.main.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentSplashBinding
import ua.dimaevseenko.format_player.fragment.main.MainFragment
import ua.dimaevseenko.format_player.fragment.main.auth.login.LoginFragment
import javax.inject.Inject

class SplashFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "SplashFragment"
    }

    private lateinit var binding: FragmentSplashBinding

    @Inject lateinit var loginFragment: LoginFragment

    private var job: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)

        job = CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            launch(Dispatchers.Main) {
                (parentFragment as MainFragment).authFragment()
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }
}