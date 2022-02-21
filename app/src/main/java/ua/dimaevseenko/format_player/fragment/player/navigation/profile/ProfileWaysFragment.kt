package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentProfileWaysBinding
import ua.dimaevseenko.format_player.model.FavouriteChannels
import ua.dimaevseenko.format_player.model.LastWatchedChannels
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.UnLoginResult
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import ua.dimaevseenko.format_player.viewmodel.RequestViewModel
import javax.inject.Inject

class ProfileWaysFragment @Inject constructor(): AnimatedFragment(), Server.Listener<UnLoginResult> {

    companion object{
        const val TAG = "ProfileWaysFragment"
    }

    private lateinit var binding: FragmentProfileWaysBinding

    @Inject lateinit var requestViewModelFactory: RequestViewModel.Factory<UnLoginResult>
    private lateinit var requestViewModel: RequestViewModel<UnLoginResult>

    var returnFragment = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileWaysBinding.bind(inflater.inflate(R.layout.fragment_profile_ways, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(returnFragment)
            animateStartX(true)

        returnFragment = false

        requestViewModel = ViewModelProvider(this, requestViewModelFactory).get(RequestViewModel::class.java) as RequestViewModel<UnLoginResult>
        requestViewModel.listener = this

        binding.personalLayout.setOnClickListener { personal() }
        binding.paymentsLayout.setOnClickListener { payments() }
        binding.button.setOnClickListener { unLogin() }

        if(requireContext().isTV)
            binding.personalLayout.requestFocus()
    }

    private fun personal(){
        if(isAnimated) {
            dismiss()
            (parentFragment as ProfileFragment).personalFragment()
        }
    }

    private fun payments(){
        if(isAnimated) {
            dismiss()
            (parentFragment as ProfileFragment).paymentsFragment()
        }
    }

    private fun unLogin(){
        showProgressDialog()
        requestViewModel.request(
            Bundle().apply {
                putString("action", "jdeldevice")
            }
        )
    }

    override fun onResponse(result: UnLoginResult) {
        dismissProgressDialog()
        if(result.status == 0)
            unLoginSuccess()
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }

    private fun unLoginSuccess(){
        Config.Values.login = null
        Config.Values.password = null
        Config.Values.mToken = null
        Config.Values.lastWatchedChannelsIds = LastWatchedChannels()
        Config.Values.favouriteChannels = FavouriteChannels()
        Config.Values.save(requireContext())
        mainFragment.authFragment()
    }

    override fun onDestroy() {
        requestViewModel.listener = null
        super.onDestroy()
    }

    private fun dismiss(){
        animateEndX(true)
    }

    override fun tag(): String {
        return TAG
    }
}