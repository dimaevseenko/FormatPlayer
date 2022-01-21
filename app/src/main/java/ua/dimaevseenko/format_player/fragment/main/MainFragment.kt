package ua.dimaevseenko.format_player.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentMainBinding
import ua.dimaevseenko.format_player.fragment.main.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.main.splash.SplashFragment
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding

    @Inject lateinit var splashFragment: SplashFragment
    @Inject lateinit var authorizationFragment: AuthorizationFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.mainFragmentContainer, splashFragment, SplashFragment.TAG, true)
    }

    fun authFragment(){
        replaceFragment(R.id.mainFragmentContainer, authorizationFragment, AuthorizationFragment.TAG, true)
    }

    fun playerActivity(){
        requireActivity().finish()
        requireActivity().startActivity(Intent(requireContext(), PlayerActivity::class.java))
    }

    fun onBackPressed(): Boolean{
        getFragment<AuthorizationFragment>(AuthorizationFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }
}