package ua.dimaevseenko.format_player.fragment.player.video

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentVideoBinding
import javax.inject.Inject

class VideoFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "VideoFragment"
    }

    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVideoBinding.bind(inflater.inflate(R.layout.fragment_video, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}