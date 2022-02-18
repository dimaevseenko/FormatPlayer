package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentProfileBinding
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.model.FavouriteChannels
import ua.dimaevseenko.format_player.model.LastWatchedChannels
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ClientResult
import ua.dimaevseenko.format_player.network.result.UnLoginResult
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import ua.dimaevseenko.format_player.viewmodel.RequestViewModel
import javax.inject.Inject

class ProfileFragment @Inject constructor(): AnimatedFragment(), Server.Listener<UnLoginResult> {

    companion object{
        const val TAG = "ProfileFragment"
    }

    private lateinit var binding: FragmentProfileBinding

    @Inject lateinit var request: Server.Request

    private lateinit var clientViewModel: ClientViewModel

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

        clientViewModel = ViewModelProvider(requireActivity()).get(ClientViewModel::class.java)

        requestViewModel = ViewModelProvider(viewModelStore, requestViewModelFactory).get(
            RequestViewModel::class.java) as RequestViewModel<UnLoginResult>
        requestViewModel.listener = this

        binding.personalLayout.setOnClickListener {
            clientViewModel.getClient()?.let {
                Log.d("CHANNELL", it.toString())
            }
        }

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
        Config.Values.password = null
        Config.Values.mToken = null
        Config.Values.lastWatchedChannelsIds = LastWatchedChannels()
        Config.Values.favouriteChannels = FavouriteChannels()
        Config.Values.save(requireContext())
        mainFragment.authFragment()
    }

    override fun onResponse(result: UnLoginResult) {
        dismissProgressDialog()
        if(result.status == 0)
            unLoginSuccess()
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }

    override fun onDestroy() {
        requestViewModel.listener = null
        super.onDestroy()
    }

    override fun tag(): String {
        return TAG
    }
}