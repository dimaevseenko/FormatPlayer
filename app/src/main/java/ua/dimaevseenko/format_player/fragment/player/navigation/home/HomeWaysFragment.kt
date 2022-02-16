package ua.dimaevseenko.format_player.fragment.player.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentHomeWaysBinding
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.FocusScaleAnimator
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class HomeWaysFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "HomeWaysFragment"
    }

    private lateinit var binding: FragmentHomeWaysBinding

    var returnFragment = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeWaysBinding.bind(inflater.inflate(R.layout.fragment_home_ways, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        if(returnFragment)
            animateStartX(reverse = true)

        if(requireContext().isTV)
            binding.channelsLayout.requestFocus()

        binding.channelsLayout.setOnClickListener {
            if(isAnimated) {
                dismiss()
                (parentFragment as HomeFragment).channelsFragment()
            }
        }
        binding.camerasLayout.setOnClickListener {
            if(isAnimated) {
                dismiss()
                (parentFragment as HomeFragment).camerasFragment()
            }
        }
    }

    private fun dismiss(){
        animateEndX(reverse = true)
    }
}