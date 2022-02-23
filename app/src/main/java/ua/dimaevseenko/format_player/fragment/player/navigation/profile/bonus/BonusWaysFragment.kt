package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.BaseFragment
import ua.dimaevseenko.format_player.databinding.FragmentBonusWaysBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.auction.AuctionsFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.gift.GiftsFragment
import ua.dimaevseenko.format_player.replaceFragment
import javax.inject.Inject

class BonusWaysFragment @Inject constructor(): Fragment(), TabLayout.OnTabSelectedListener {

    companion object{
        const val TAG = "BonusWaysFragment"
    }

    private lateinit var binding: FragmentBonusWaysBinding

    @Inject lateinit var giftsFragment: GiftsFragment
    @Inject lateinit var auctionsFragment: AuctionsFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBonusWaysBinding.bind(inflater.inflate(R.layout.fragment_bonus_ways, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        binding.bonusWaysTabLayout.addOnTabSelectedListener(this)

        if(savedInstanceState == null)
            replaceFragment(R.id.bonusWaysContainer, giftsFragment, GiftsFragment.TAG, true)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        when(tab.text){
            getString(R.string.gifts) -> onWayItemSelected(giftsFragment)
            getString(R.string.auctions) -> onWayItemSelected(auctionsFragment)
        }
    }

    private fun onWayItemSelected(fragment: BaseFragment){
        replaceFragment(R.id.bonusWaysContainer, fragment, fragment.tag(), true)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            binding.bonusWaysTabLayout.selectTab(binding.bonusWaysTabLayout.getTabAt(it.getInt("selectedTabPosition")))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("selectedTabPosition", binding.bonusWaysTabLayout.selectedTabPosition)
    }
}