package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentStreamControlsChannelBinding
import ua.dimaevseenko.format_player.fragment.player.stream.ControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.settings.ChannelSettingsFragment
import ua.dimaevseenko.format_player.model.FavouriteChannel
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelControlsFragment @Inject constructor(): ControlsFragment(), Server.Listener<ProgramsResult> {

    private lateinit var binding: FragmentStreamControlsChannelBinding

    private lateinit var programsViewModel: ProgramsViewModel

    @Inject lateinit var channelProgramsFragment: ChannelProgramsFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStreamControlsChannelBinding.bind(inflater.inflate(R.layout.fragment_stream_controls_channel, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        programsViewModel = ViewModelProvider(requireActivity()).get(ProgramsViewModel::class.java)
        programsViewModel.addListener(TAG, this)
        initView()
    }

    private fun initView(){
        if(requireContext().isTV)
            binding.hidePlayerImageButton.requestFocus()

        updateFavouriteIcon()

        binding.titleTextView.text = getStream().getStreamTitle()
        binding.hidePlayerImageButton.setOnClickListener { dismissStream() }
        loadName()

        binding.fullscreenImageButton.setOnClickListener { fullscreen() }
        binding.settingsImageButton.setOnClickListener { settings() }
        binding.favouriteImageButton.setOnClickListener { favourite() }
        binding.programsImageButton?.let { it.setOnClickListener { programs() } }
    }

    private fun loadName(){
        if(programsViewModel.getCurrentProgram(getStream().getStreamId()) != null)
            binding.nameTextView.text = programsViewModel.getCurrentProgram(getStream().getStreamId())!!.name
        else
            binding.nameTextView.text = getString(R.string.updating)
    }

    private fun updateFavouriteIcon(){
        if(Config.Values.favouriteChannels.findFavourite(getStream().getStreamId()) != null)
            binding.favouriteImageButton.setImageResource(R.drawable.ic_heart)
        else
            binding.favouriteImageButton.setImageResource(R.drawable.ic_heart_fill)
    }

    private fun favourite(){
        if(Config.Values.favouriteChannels.findFavourite(getStream().getStreamId()) != null)
            Config.Values.favouriteChannels.remove(Config.Values.favouriteChannels.findFavourite(getStream().getStreamId()))
        else
            Config.Values.favouriteChannels.add(FavouriteChannel(getStream().getStreamId(), System.currentTimeMillis()))

        Config.Values.favouriteChannels.sortBy { it.dateAdded }
        Config.Values.save(requireContext())
        updateFavouriteIcon()
    }

    private fun settings(){
        appComponent.createChannelSettingsFragment().apply {
            show(this@ChannelControlsFragment.parentFragmentManager, ChannelSettingsFragment.TAG)
        }
    }

    private fun programs(){
        if(programsViewModel.getPrograms(getStream().getStreamId()) == null)
            return

        binding.streamProgramsContainer?.let {
            if(getFragment<ChannelProgramsFragment>(ChannelProgramsFragment.TAG) == null) {
                addFragment(it.id, channelProgramsFragment, ChannelProgramsFragment.TAG, true)
                stopTimer()
            }
            else
                destroyPrograms()
        }
    }

    private fun destroyPrograms(){
        removeFragment(
            getFragment<ChannelProgramsFragment>(ChannelProgramsFragment.TAG)!!,
            true,
            FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
        )
        startTimer()
    }

    override fun onResume() {
        super.onResume()
        if(getFragment<ChannelProgramsFragment>(ChannelProgramsFragment.TAG) != null && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            stopTimer()
    }

    override fun onResponse(result: ProgramsResult) {
        binding.nameTextView.text = result.requirePrograms().getCurrentProgram().name
    }

    override fun onFailure(t: Throwable) {}

    override fun onDestroy() {
        programsViewModel.removeListener(TAG)
        super.onDestroy()
    }

    fun onBackPressed(): Boolean{
        getFragment<ChannelProgramsFragment>(ChannelProgramsFragment.TAG)?.let {
            destroyPrograms()
            binding.programsImageButton?.requestFocus()
            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
        if(getFragment<ChannelProgramsFragment>(ChannelProgramsFragment.TAG) == null)
            super.onKeyDown(keyCode, event)
    }
}