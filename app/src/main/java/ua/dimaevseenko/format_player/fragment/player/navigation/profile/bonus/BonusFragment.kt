package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.addFragment
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentBonusBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import ua.dimaevseenko.format_player.replaceFragment
import javax.inject.Inject

class BonusFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "BonusFragment"
    }

    private lateinit var binding: FragmentBonusBinding

    @Inject lateinit var bonusLoaderFragment: BonusLoaderFragment
    @Inject lateinit var bonusWaysFragment: BonusWaysFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBonusBinding.bind(inflater.inflate(R.layout.fragment_bonus, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null) {
            animateStartX()
            addFragment(R.id.bonusContainer, bonusLoaderFragment, BonusLoaderFragment.TAG, true)
        }

        binding.backImageView.setOnClickListener { dismiss() }
    }

    fun bonusLoaded(){
        if(isAdded){
            replaceFragment(R.id.bonusContainer, bonusWaysFragment, BonusWaysFragment.TAG, true)
        }
    }

    fun dismiss(){
        if(isAnimated){
            animateEndX()
            (parentFragment as ProfileFragment).profileWaysFragment()
        }
    }

    override fun tag(): String {
        return TAG
    }
}