package ua.dimaevseenko.format_player.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.model.Cams
import ua.dimaevseenko.format_player.model.Channels
import ua.dimaevseenko.format_player.model.Genre
import ua.dimaevseenko.format_player.model.Genres
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.IconsResult
import ua.dimaevseenko.format_player.network.result.PlaylistResult
import javax.inject.Inject

class PlaylistViewModel @Inject constructor(): ViewModel(){

    @Inject lateinit var serverRequest: Server.Request

    @Inject lateinit var context: Context

    private val playlistLiveData = MutableLiveData<PlaylistResult>()

    var listener: Server.Listener<PlaylistResult>? = null

    fun getGenres(): Genres? {
        return playlistLiveData.value?.genres
    }

    fun getChannels(): Channels?{
        return playlistLiveData.value?.channels
    }

    fun getCameras(): Cams?{
        return playlistLiveData.value?.cams
    }

    fun loadPlaylist(){
        this.listener = listener

        serverRequest.request(
            Bundle().apply {
                putString("action", "jgetchannellist")
            },
            PlaylistCallback()
        )
    }

    private fun onResponsePlaylist(response: Response<PlaylistResult>){
        response.body()?.let { it ->
            it.genres.add(0, Genre("0", context.resources.getString(R.string.all)))
            it.genres.add(0, Genre("-1", context.resources.getString(R.string.last)))
            it.genres = it.genres.noEmpty(it.channels)
            it.channels.sortBy { channel -> channel.id.toInt() }
            it.cams.sortBy { cam -> cam.id.toInt() }
            playlistLiveData.value = it
            loadIcons()
        }
    }

    private fun loadIcons(){
        serverRequest.request(
            Bundle().apply {
                putString("action", "jgetjsoniconschannels")
            },
            IconsCallback()
        )
    }

    private fun onResponseIcons(response: Response<IconsResult>){
        response.body()?.let {
            CoroutineScope(Dispatchers.Default).launch {
                playlistLiveData.value!!.channels.setIcons(it.icons)
                playlistLiveData.value!!.cams.setIcons(it.icons)
                launch(Dispatchers.Default) { listener?.onResponse(playlistLiveData.value!!) }
            }
        }
    }

    private inner class PlaylistCallback: Callback<PlaylistResult>{
        override fun onResponse(call: Call<PlaylistResult>, response: Response<PlaylistResult>) {
            onResponsePlaylist(response)
        }

        override fun onFailure(call: Call<PlaylistResult>, t: Throwable) {
            listener?.onFailure(t)
        }
    }

    private inner class IconsCallback: Callback<IconsResult>{
        override fun onResponse(call: Call<IconsResult>, response: Response<IconsResult>) {
            onResponseIcons(response)
        }

        override fun onFailure(call: Call<IconsResult>, t: Throwable) {
            listener?.onFailure(t)
        }
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var playlistViewModel: PlaylistViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return playlistViewModel as T
        }
    }
}