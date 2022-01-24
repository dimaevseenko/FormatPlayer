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
import ua.dimaevseenko.format_player.fragment.player.video.VideoFragment
import javax.inject.Inject

class PlayerNavFragment @Inject constructor(): Fragment(), NavigationBarView.OnItemSelectedListener {

    companion object{
        const val TAG = "PlayerNavFragment"
    }

    private lateinit var binding: FragmentPlayerNavigationBinding

    @Inject lateinit var videoFragment: VideoFragment

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

    fun addVideoFragment(){
        parentFragment?.addFragment(R.id.playerContainer, videoFragment, VideoFragment.TAG, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navMain -> { onNavItemSelected(HomeFragment()) }
            R.id.navSearch -> { onNavItemSelected(HomeFragment()) }
            R.id.navFavourite -> { onNavItemSelected(HomeFragment()) }
            R.id.navProfile -> { onNavItemSelected(HomeFragment()) }
        }
        return true
    }

    private fun onNavItemSelected(fragment: NavFragment): Boolean{
        replaceFragment(R.id.playerNavContainer, fragment, fragment.tag(), true)
        return false
    }
}