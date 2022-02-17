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
import ua.dimaevseenko.format_player.databinding.FragmentLoaderBinding
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ClientResult
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import ua.dimaevseenko.format_player.removeFragment
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class LoaderFragment @Inject constructor(): Fragment(){

    companion object{
        const val TAG = "LoaderFragment"
    }

    private lateinit var binding: FragmentLoaderBinding

    @Inject lateinit var playlistViewModelFactory: PlaylistViewModel.Factory
    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var clientViewModelFactory: ClientViewModel.Factory
    private lateinit var clientViewModel: ClientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoaderBinding.bind(inflater.inflate(R.layout.fragment_loader, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        clientViewModel = ViewModelProvider(requireActivity(), clientViewModelFactory).get(ClientViewModel::class.java)
        clientViewModel.listener = ClientListener()

        playlistViewModel = ViewModelProvider(requireActivity(), playlistViewModelFactory).get(PlaylistViewModel::class.java)
        playlistViewModel.listener = PlaylistListener()

        playlistViewModel.loadPlaylist()
    }

    private inner class PlaylistListener: Server.Listener<PlaylistResult>{
        override fun onResponse(result: PlaylistResult) {
            clientViewModel.loadClient()
        }

        override fun onFailure(t: Throwable) {

        }
    }

    private inner class ClientListener: Server.Listener<ClientResult>{
        override fun onResponse(result: ClientResult) {
            (parentFragment as PlayerNavFragment).playlistLoaded()
        }

        override fun onFailure(t: Throwable) {

        }
    }

    override fun onDestroy() {
        playlistViewModel.listener = null
        clientViewModel.listener = null
        super.onDestroy()
    }
}