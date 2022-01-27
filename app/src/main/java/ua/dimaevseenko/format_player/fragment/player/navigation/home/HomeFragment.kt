package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentHomeBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.cameras.CamerasFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.ChannelsFragment
import javax.inject.Inject

class HomeFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "HomeFragment"
    }

    private lateinit var binding: FragmentHomeBinding

    @Inject lateinit var homeWaysFragment: HomeWaysFragment
    @Inject lateinit var channelsFragment: ChannelsFragment
    @Inject lateinit var camerasFragment: CamerasFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            addFragment(R.id.homeContainer, homeWaysFragment, HomeWaysFragment.TAG, true)
    }

    fun channelsFragment(){
        channelsFragment = ChannelsFragment()
        replaceFragment(R.id.homeContainer, channelsFragment, ChannelsFragment.TAG, true)
    }

    fun homeWaysFragment(){
        homeWaysFragment = HomeWaysFragment()
        homeWaysFragment.returnFragment = true
        replaceFragment(R.id.homeContainer, homeWaysFragment, HomeWaysFragment.TAG, true)
    }

    fun camerasFragment(){
        camerasFragment = CamerasFragment()
        replaceFragment(R.id.homeContainer, camerasFragment, CamerasFragment.TAG, true)
    }

    fun onBackPressed(): Boolean{
        getFragment<ChannelsFragment>(ChannelsFragment.TAG)?.let {
            it.dismiss()
            return true
        }
        getFragment<CamerasFragment>(CamerasFragment.TAG)?.let {
            it.dismiss()
            return true
        }
        return false
    }

    override fun tag(): String {
        return TAG
    }
}