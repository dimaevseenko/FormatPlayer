package ua.dimaevseenko.format_player.fragment.player.stream.channel.settings

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentSettingsBinding
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamFragment
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Quality
import ua.dimaevseenko.format_player.model.Stream
import javax.inject.Inject

class ChannelSettingsFragment @Inject constructor(): BottomSheetDialogFragment(),
    SettingsSwipeHelper.Listener {

    companion object{
        const val TAG = "SettingsChannelFragment"
    }

    private lateinit var binding: FragmentSettingsBinding

    @Inject lateinit var settingsSwipeHelperFactory: SettingsSwipeHelper.Factory
    private lateinit var settingsSwipeHelper: SettingsSwipeHelper

    @Inject lateinit var channelQualityFragment: ChannelQualityFragment

    private var isDismiss = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_FormatPlayer_BottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        binding = FragmentSettingsBinding.bind(inflater.inflate(R.layout.fragment_settings, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        settingsSwipeHelper = settingsSwipeHelperFactory.createSwipeHelper(binding.rootContent).apply {
            setListener(this@ChannelSettingsFragment)
        }

        if(requireContext().isTV)
            binding.qualityLayout.requestFocus()

        initView()
    }

    private fun initView(){
        val quality = (parentFragment as ChannelStreamFragment).getStreamQuality()

        val qualityString = if(quality == Quality.FULL_HD)
            getString(R.string.full_hd)
        else if(quality == Quality.HD)
            getString(R.string.hd)
        else if(quality == Quality.AUTO)
            getString(R.string.auto)
        else
            (parentFragment as ChannelStreamFragment).getStreamQuality().toString()

        binding.qualityTextView.text = qualityString
        binding.rootContent.setOnTouchListener(settingsSwipeHelper)
        binding.qualityLayout.setOnClickListener { quality() }
        binding.root.setOnClickListener { dismiss() }
    }

    private fun quality(){
        channelQualityFragment.show(parentFragmentManager, ChannelQualityFragment.TAG)
        dismiss()
    }

    override fun dismiss() {
        if(!isDismiss)
            settingsSwipeHelper.dismiss {
                super.dismiss()

                if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    (parentFragment as ChannelStreamFragment).hideSystemUI()
            }
        isDismiss = true
    }

    override fun onSwiped(close: Boolean) {
        if(close)
            dismiss()
    }
}