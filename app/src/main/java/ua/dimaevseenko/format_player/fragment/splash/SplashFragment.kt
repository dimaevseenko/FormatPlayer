package ua.dimaevseenko.format_player.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentSplashBinding
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.PresentationPlayer
import ua.dimaevseenko.format_player.fragment.auth.login.LoginFragment
import javax.inject.Inject

class SplashFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "SplashFragment"
    }

    private lateinit var binding: FragmentSplashBinding

    @Inject lateinit var loginFragment: LoginFragment

    @Inject lateinit var presentationPlayerFactory: PresentationPlayer.Factory
    private lateinit var presentationPlayer: PresentationPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        presentationPlayer = presentationPlayerFactory.createPresentationPlayer(binding.player, false, "intro.mp4"){
           checkLogin()
        }
        presentationPlayer.play()
    }

    private fun checkLogin(){
        if(Config.Values.login != null && Config.Values.mToken != null)
            (parentFragment as MainFragment).playerFragment()
        else
            (parentFragment as MainFragment).authFragment()
    }

    override fun onResume() {
        presentationPlayer.play()
        super.onResume()
    }

    override fun onPause() {
        presentationPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        presentationPlayer.stop()
        super.onDestroy()
    }

    fun onBackPressed(): Boolean = true
}