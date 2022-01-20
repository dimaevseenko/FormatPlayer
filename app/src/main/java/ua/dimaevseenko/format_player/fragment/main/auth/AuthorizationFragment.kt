package ua.dimaevseenko.format_player.fragment.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.addFragment
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentAuthBinding
import ua.dimaevseenko.format_player.fragment.main.auth.login.LoginFragment
import javax.inject.Inject

class AuthorizationFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "AuthorizationFragment"
    }

    private lateinit var binding: FragmentAuthBinding

    @Inject lateinit var loginFragment: LoginFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthBinding.bind(inflater.inflate(R.layout.fragment_auth, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.authContainer, loginFragment, LoginFragment.TAG)
    }
}