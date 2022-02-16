package ua.dimaevseenko.format_player.fragment.player.navigation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentSearchBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.RecyclerChannelsAdapter
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.VerticalChannelsAdapter
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Channel
import ua.dimaevseenko.format_player.playerFragment
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class SearchFragment @Inject constructor(): AnimatedFragment(), RecyclerChannelsAdapter.Listener {

    companion object{
        const val TAG = "SearchFragment"
    }

    private lateinit var binding: FragmentSearchBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var verticalChannelsAdapterFactory: VerticalChannelsAdapter.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartY()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        initView()
    }

    private fun initView(){
        binding.editTextTextPersonName.addTextChangedListener(SearchTextWatcher())
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = verticalChannelsAdapterFactory.createVerticalChannelsAdapter(
            playlistViewModel.getChannels()!!
        ).apply {
            setListener(this@SearchFragment)
        }

        if(requireContext().isTV)
            binding.editTextTextPersonName.requestFocus()
    }

    override fun onSelectedChannel(channel: Channel, position: Int, focusedView: View?) {
        playerFragment.startChannel(channel){
            if(requireContext().isTV)
                focusedView?.requestFocus()
        }
    }

    private inner class SearchTextWatcher: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            (binding.recycler.adapter as VerticalChannelsAdapter).updateChannels(playlistViewModel.getChannels()!!.getChannelsForName(s.toString().trim()))
            binding.recycler.scrollToPosition(0)
        }
    }

    override fun tag(): String {
        return TAG
    }
}