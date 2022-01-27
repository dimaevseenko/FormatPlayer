package ua.dimaevseenko.format_player.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentAuthBinding
import ua.dimaevseenko.format_player.fragment.PresentationPlayer
import ua.dimaevseenko.format_player.fragment.auth.login.LoginFragment
import ua.dimaevseenko.format_player.fragment.auth.register.RegisterFragment
import ua.dimaevseenko.format_player.fragment.MainFragment
import javax.inject.Inject

class AuthorizationFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "AuthorizationFragment"
    }

    private lateinit var binding: FragmentAuthBinding

    @Inject lateinit var loginFragment: LoginFragment
    @Inject lateinit var registerFragment: RegisterFragment

    @Inject lateinit var presentationPlayerFactory: PresentationPlayer.Factory
    lateinit var presentationPlayer: PresentationPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthBinding.bind(inflater.inflate(R.layout.fragment_auth, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        if(savedInstanceState == null)
            addFragment(R.id.authContainer, loginFragment, LoginFragment.TAG)

        presentationPlayer = presentationPlayerFactory.createPresentationPlayer(binding.playerView, true, "tv-presentation.mp4").apply { play() }
    }

    fun registerFragment(){
        loginFragment = getFragment(LoginFragment.TAG)!!
        replaceFragment(R.id.authContainer, registerFragment, RegisterFragment.TAG, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    }

    fun loginFragment(){
        registerFragment = getFragment(RegisterFragment.TAG)!!
        replaceFragment(R.id.authContainer, loginFragment, LoginFragment.TAG, true, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
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

    fun onBackPressed(): Boolean{
        getFragment<RegisterFragment>(RegisterFragment.TAG)?.let {
            loginFragment()
            return true
        }
        return false
    }
}