package ua.dimaevseenko.format_player.fragment.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentPlayerBinding
import ua.dimaevseenko.format_player.dismissProgressDialog
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.auth.RequestViewModel
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.UnLoginResult
import ua.dimaevseenko.format_player.showProgressDialog
import javax.inject.Inject

class PlayerFragment @Inject constructor(): Fragment(), Server.Listener<UnLoginResult> {

    companion object{
        const val TAG = "PlayerFragment"
    }

    private lateinit var binding: FragmentPlayerBinding

    @Inject lateinit var requestViewModelFactory: RequestViewModel.Factory<UnLoginResult>
    private lateinit var requestViewModel: RequestViewModel<UnLoginResult>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        requestViewModel = ViewModelProvider(viewModelStore, requestViewModelFactory).get(RequestViewModel::class.java) as RequestViewModel<UnLoginResult>
        requestViewModel.listener = this

        binding.button.setOnClickListener {
            unLogin()
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
        println(result)
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }

    private fun unLoginSuccess(){
        Config.Values.login = null
        Config.Values.mToken = null
        Config.Values.save(requireContext())
        (parentFragment as MainFragment).authFragment()
    }
}