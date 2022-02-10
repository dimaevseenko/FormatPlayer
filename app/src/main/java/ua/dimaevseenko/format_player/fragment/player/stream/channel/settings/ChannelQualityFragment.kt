package ua.dimaevseenko.format_player.fragment.player.stream.channel.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentSettingsQualityBinding
import ua.dimaevseenko.format_player.fragment.player.stream.SwipeHelper
import javax.inject.Inject

class ChannelQualityFragment @Inject constructor(): BottomSheetDialogFragment(), SettingsSwipeHelper.Listener {

    companion object {
        const val TAG = "ChannelQualityFragment"
    }

    private lateinit var binding: FragmentSettingsQualityBinding

    @Inject
    lateinit var settingsSwipeHelperFactory: SettingsSwipeHelper.Factory
    private lateinit var settingsSwipeHelper: SettingsSwipeHelper

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_FormatPlayer_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsQualityBinding.bind(
            inflater.inflate(
                R.layout.fragment_settings_quality,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        settingsSwipeHelper =
            settingsSwipeHelperFactory.createSwipeHelper(binding.rootContent).apply {
                setListener(this@ChannelQualityFragment)
            }

        binding.root.setOnClickListener { dismiss() }
        binding.swipeCardView.setOnTouchListener(settingsSwipeHelper)
    }

    override fun onSwiped(close: Boolean) {
        if(close)
            dismiss()
    }

    override fun dismiss() {
        settingsSwipeHelper.dismiss {
            super.dismiss()
        }
    }
}
