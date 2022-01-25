package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentProfileBinding
import ua.dimaevseenko.format_player.dismissProgressDialog
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.viewmodel.RequestViewModel
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.UnLoginResult
import ua.dimaevseenko.format_player.showProgressDialog
import javax.inject.Inject

class ProfileFragment @Inject constructor(): AnimatedFragment(), Server.Listener<UnLoginResult> {

    companion object{
        const val TAG = "ProfileFragment"
    }

    private lateinit var binding: FragmentProfileBinding

    @Inject lateinit var requestViewModelFactory: RequestViewModel.Factory<UnLoginResult>
    private lateinit var requestViewModel: RequestViewModel<UnLoginResult>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_profile, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
            animateStartY()

        appComponent.inject(this)

        requestViewModel = ViewModelProvider(viewModelStore, requestViewModelFactory).get(
            RequestViewModel::class.java) as RequestViewModel<UnLoginResult>
        requestViewModel.listener = this

        binding.button.setOnClickListener { unLogin() }
    }

    private fun unLogin(){
        showProgressDialog()
        requestViewModel.request(
            Bundle().apply {
                putString("action", "jdeldevice")
                putString("authmac", Config.getFullToken(requireContext()))
            }
        )
    }

    private fun unLoginSuccess(){
        Config.Values.login = null
        Config.Values.mToken = null
        Config.Values.save(requireContext())
        (parentFragment?.parentFragment?.parentFragment as MainFragment).authFragment()
    }

    override fun onResponse(result: UnLoginResult) {
        dismissProgressDialog()
        if(result.status == 0)
            unLoginSuccess()
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }

    override fun tag(): String {
        return TAG
    }
}