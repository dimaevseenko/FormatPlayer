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

class ProfileFragment @Inject constructor(): AnimatedFragment(){

    companion object{
        const val TAG = "ProfileFragment"
    }

    private lateinit var binding: FragmentProfileBinding

    @Inject lateinit var profileLoaderFragment: ProfileLoaderFragment
    @Inject lateinit var profileWaysFragment: ProfileWaysFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_profile, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            replaceFragment(R.id.profileContainer, profileLoaderFragment, ProfileLoaderFragment.TAG, true)
    }

    fun clientLoaded(){
        if(isAdded)
            replaceFragment(R.id.profileContainer, profileWaysFragment, ProfileWaysFragment.TAG, true)
    }

    override fun tag(): String {
        return TAG
    }
}