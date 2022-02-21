package ua.dimaevseenko.format_player.fragment.player.navigation.profile.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentPaymentsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import javax.inject.Inject

class PaymentsFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "PaymentsFragment"
    }

    private lateinit var binding: FragmentPaymentsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPaymentsBinding.bind(inflater.inflate(R.layout.fragment_payments, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
            animateStartX()

        binding.backImageView.setOnClickListener { dismiss() }
    }

    fun dismiss(){
        if(isAnimated) {
            animateEndX()
            (parentFragment as ProfileFragment).profileWaysFragment()
        }
    }

    override fun tag(): String {
        return TAG
    }
}