package ua.dimaevseenko.format_player.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import javax.inject.Inject

class PlaylistViewModel @Inject constructor(): ViewModel(), Callback<PlaylistResult> {

    @Inject lateinit var serverRequest: Server.Request

    private val playlistLiveData = MutableLiveData<PlaylistResult>()

    var listener: Server.Listener<PlaylistResult>? = null

    fun getGenres(){
        if(playlistLiveData.value == null)
            loadPlaylist()
        else
            listener?.onResponse(playlistLiveData.value!!)
    }

    private fun loadPlaylist(){
        serverRequest.request(
            Bundle().apply {
                putString("action", "jgetchannellist")
            },
            this
        )
    }

    override fun onResponse(call: Call<PlaylistResult>?, response: Response<PlaylistResult>) {
        response.body()?.let {
            playlistLiveData.value = it
            listener?.onResponse(it)
        }
    }

    override fun onFailure(call: Call<PlaylistResult>, t: Throwable) {
        listener?.onFailure(t)
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var playlistViewModel: PlaylistViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return playlistViewModel as T
        }
    }
}