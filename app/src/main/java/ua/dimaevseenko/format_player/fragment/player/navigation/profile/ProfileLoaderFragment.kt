package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentProfileLoaderBinding
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ClientResult
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import javax.inject.Inject

class ProfileLoaderFragment @Inject constructor(): Fragment(), Server.Listener<ClientResult> {

    companion object{
        const val TAG = "ProfileLoaderFragment"
    }

    private lateinit var binding: FragmentProfileLoaderBinding

    @Inject lateinit var clientViewModelFactory: ClientViewModel.Factory
    private lateinit var clientViewModel: ClientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentProfileLoaderBinding.bind(inflater.inflate(R.layout.fragment_profile_loader, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        clientViewModel = ViewModelProvider(requireActivity(), clientViewModelFactory).get(ClientViewModel::class.java)
        clientViewModel.listener = this
        clientViewModel.getClient()?.let { (parentFragment as ProfileFragment).clientLoaded() }
    }

    override fun onResponse(result: ClientResult) {
        (parentFragment as ProfileFragment).clientLoaded()
    }

    override fun onFailure(t: Throwable) {

    }

    override fun onDestroy() {
        clientViewModel.listener = null
        super.onDestroy()
    }
}