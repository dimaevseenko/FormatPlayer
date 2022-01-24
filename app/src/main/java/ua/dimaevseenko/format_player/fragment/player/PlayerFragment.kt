package ua.dimaevseenko.format_player.fragment.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentPlayerBinding
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.RequestViewModel
import ua.dimaevseenko.format_player.fragment.player.home.HomeFragment
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.UnLoginResult
import javax.inject.Inject

class PlayerFragment @Inject constructor(): Fragment(), NavigationBarView.OnItemSelectedListener {

    companion object{
        const val TAG = "PlayerFragment"
    }

    private lateinit var binding: FragmentPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        binding.playerNavigationView.setOnItemSelectedListener(this)
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
        replaceFragment(R.id.playerContainer, fragment, fragment.tag(), true)
        return false
    }
}