package ua.dimaevseenko.format_player.fragment.player.stream.channel.settings

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginTop
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentSettingsQualityBinding
import ua.dimaevseenko.format_player.fragment.player.stream.SwipeHelper
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamFragment
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Quality
import ua.dimaevseenko.format_player.model.Stream
import ua.dimaevseenko.format_player.model.Type
import javax.inject.Inject

class ChannelQualityFragment @Inject constructor(): BottomSheetDialogFragment(), SettingsSwipeHelper.Listener{

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsQualityBinding.bind(inflater.inflate(R.layout.fragment_settings_quality, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        settingsSwipeHelper =
            settingsSwipeHelperFactory.createSwipeHelper(binding.rootContent).apply {
                setListener(this@ChannelQualityFragment)
            }

        if(requireContext().isTV)
            binding.autoLayout.requestFocus()

        initView()
    }

    private fun getPxFromDp(dp: Float): Int{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    private fun initView(){
        binding.root.setOnClickListener { dismiss() }
        binding.swipeCardView.setOnTouchListener(settingsSwipeHelper)

        if((parentFragment as ChannelStreamFragment).getStream().getStreamType() == Type.SD){
            binding.fullHdLayout.visibility = View.GONE
            binding.hdLayout.visibility = View.GONE

            binding.sdLayout.apply {
                (layoutParams as ViewGroup.MarginLayoutParams).apply {
                    topMargin = getPxFromDp(8f)
                }
                nextFocusUpId = id
            }
        }

        binding.fullHdLayout.setOnClickListener{ setQuality(Quality.FULL_HD) }
        binding.hdLayout.setOnClickListener{ setQuality(Quality.HD) }
        binding.sdLayout.setOnClickListener{ setQuality(Quality.SD) }
        binding.midLayout.setOnClickListener{ setQuality(Quality.MID) }
        binding.lowLayout.setOnClickListener{ setQuality(Quality.LOW) }
        binding.autoLayout.setOnClickListener{ setQuality(Quality.AUTO) }
    }

    private fun setQuality(quality: Quality){
        (parentFragment as ChannelStreamFragment).setStreamQuality(quality)
        dismiss()
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
