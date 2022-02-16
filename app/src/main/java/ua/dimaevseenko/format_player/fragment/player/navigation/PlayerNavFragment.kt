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
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.base.BaseFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.favourite.FavouriteFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.search.SearchFragment
import javax.inject.Inject

class PlayerNavFragment @Inject constructor(): Fragment(), NavigationBarView.OnItemSelectedListener {

    companion object{
        const val TAG = "PlayerNavFragment"
    }

    private lateinit var binding: FragmentPlayerNavigationBinding

    @Inject lateinit var loaderFragment: LoaderFragment

    @Inject lateinit var homeFragment: HomeFragment
    @Inject lateinit var searchFragment: SearchFragment
    @Inject lateinit var favouriteFragment: FavouriteFragment
    @Inject lateinit var profileFragment: ProfileFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerNavigationBinding.bind(inflater.inflate(R.layout.fragment_player_navigation, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.playerNavContainer, loaderFragment, LoaderFragment.TAG, true)

        binding.playerNavigationView.setOnItemSelectedListener(this)
    }

    fun playlistLoaded(){
        replaceFragment(R.id.playerNavContainer, homeFragment, HomeFragment.TAG, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(getFragment<LoaderFragment>(LoaderFragment.TAG) != null)
            return false
        when(item.itemId){
            R.id.navMain -> { onNavItemSelected(homeFragment) }
            R.id.navSearch -> { onNavItemSelected(searchFragment) }
            R.id.navFavourite -> { onNavItemSelected(favouriteFragment) }
            R.id.navProfile -> { onNavItemSelected(profileFragment) }
        }
        return true
    }

    fun onBackPressed(): Boolean{
        if(getFragment<HomeFragment>(HomeFragment.TAG) == null){
            binding.playerNavigationView.selectedItemId = R.id.navMain
            return true
        }else
            getFragment<HomeFragment>(HomeFragment.TAG)?.let { return it.onBackPressed() }
        return false
    }

    private fun onNavItemSelected(fragment: BaseFragment): Boolean{
        if(getFragment<AnimatedFragment>(fragment.tag()) == null)
            replaceFragment(R.id.playerNavContainer, fragment, fragment.tag(), true)
        return false
    }
}