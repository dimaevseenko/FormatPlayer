package ua.dimaevseenko.format_player.fragment.player.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentPlayerNavigationBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import javax.inject.Inject

class PlayerNavFragment @Inject constructor(): Fragment(), NavigationBarView.OnItemSelectedListener {

    companion object{
        const val TAG = "PlayerNavFragment"
    }

    private lateinit var binding: FragmentPlayerNavigationBinding

    @Inject lateinit var homeFragment: HomeFragment
    @Inject lateinit var profileFragment: ProfileFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerNavigationBinding.bind(inflater.inflate(R.layout.fragment_player_navigation, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        binding.playerNavigationView.setOnItemSelectedListener(this)

        if(savedInstanceState == null)
            onNavigationItemSelected(binding.playerNavigationView.menu.findItem(R.id.navMain))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navMain -> { onNavItemSelected(homeFragment) }
            R.id.navSearch -> { onNavItemSelected(homeFragment) }
            R.id.navFavourite -> { onNavItemSelected(homeFragment) }
            R.id.navProfile -> { onNavItemSelected(profileFragment) }
        }
        return true
    }

    fun onBackPressed(): Boolean{
        getFragment<HomeFragment>(HomeFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }

    private fun onNavItemSelected(fragment: AnimatedFragment): Boolean{
        if(getFragment<AnimatedFragment>(fragment.tag()) == null)
            replaceFragment(R.id.playerNavContainer, fragment, fragment.tag(), true)
        return false
    }
}