package ua.dimaevseenko.format_player.fragment.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentAuthBinding
import ua.dimaevseenko.format_player.fragment.main.auth.login.LoginFragment
import ua.dimaevseenko.format_player.fragment.main.auth.register.RegisterFragment
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

        presentationPlayer = presentationPlayerFactory.createPresentationPlayer(binding.player).apply { play() }
    }

    fun registerFragment(){
        loginFragment = getFragment(LoginFragment.TAG)!!
        removeFragment(loginFragment, true)
        addFragment(R.id.authContainer, registerFragment, RegisterFragment.TAG,true)
    }

    fun loginFragment(){
        registerFragment = getFragment(RegisterFragment.TAG)!!
        removeFragment(registerFragment, true)
        addFragment(R.id.authContainer, loginFragment, LoginFragment.TAG, true)
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