package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentPersonalBinding
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class PersonalFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "PersonalFragment"
    }

    private lateinit var binding: FragmentPersonalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPersonalBinding.bind(inflater.inflate(R.layout.fragment_personal, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
            animateStartX()

        if(requireContext().isTV)
            binding.backImageView.requestFocus()

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