package ua.dimaevseenko.format_player.fragment.player.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.DialogFragmentProgressBinding
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import ua.dimaevseenko.format_player.removeFragment
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class LoaderFragment @Inject constructor(): Fragment(), Server.Listener<PlaylistResult> {

    companion object{
        const val TAG = "LoaderFragment"
    }

    private lateinit var binding: DialogFragmentProgressBinding

    @Inject lateinit var playlistViewModelFactory: PlaylistViewModel.Factory
    private lateinit var playlistViewModel: PlaylistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentProgressBinding.bind(inflater.inflate(R.layout.dialog_fragment_progress, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        playlistViewModel = ViewModelProvider(requireActivity(), playlistViewModelFactory).get(PlaylistViewModel::class.java)
        playlistViewModel.listener = this
        playlistViewModel.loadPlaylist()
    }

    override fun onResponse(result: PlaylistResult) {
        (parentFragment as PlayerNavFragment).playlistLoaded()
    }

    override fun onFailure(t: Throwable) {

    }

    override fun onDestroy() {
        playlistViewModel.listener = null
        super.onDestroy()
    }
}