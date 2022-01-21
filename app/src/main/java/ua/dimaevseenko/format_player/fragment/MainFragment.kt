package ua.dimaevseenko.format_player.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentMainBinding
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.player.PlayerFragment
import ua.dimaevseenko.format_player.fragment.splash.SplashFragment
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding

    @Inject lateinit var splashFragment: SplashFragment
    @Inject lateinit var authorizationFragment: AuthorizationFragment
    @Inject lateinit var playerFragment: PlayerFragment

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

    fun playerFragment(){
        replaceFragment(R.id.mainFragmentContainer, playerFragment, PlayerFragment.TAG, true)
    }

    fun onBackPressed(): Boolean{
        getFragment<AuthorizationFragment>(AuthorizationFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }
}