package ua.dimaevseenko.format_player.fragment.player.navigation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentFavouriteBinding
import javax.inject.Inject

class FavouriteFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "FavouriteFragment"
    }

    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavouriteBinding.bind(inflater.inflate(R.layout.fragment_favourite, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartY()
    }
}