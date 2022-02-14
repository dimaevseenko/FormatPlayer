package ua.dimaevseenko.format_player.viewmodel

import android.os.Bundle
import androidx.collection.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.model.Programs
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import javax.inject.Inject

class ProgramsViewModel @Inject constructor(): ViewModel(), Callback<ProgramsResult> {

    @Inject lateinit var serverRequest: Server.Request

    private var liveData = MutableLiveData<ArrayMap<String, ProgramsResult>>()
    private var listeners = ArrayMap<String, Server.Listener<ProgramsResult>?>()

    private var isLoading = false

    fun addListener(tag: String, listener: Server.Listener<ProgramsResult>){
        listeners[tag] = listener
    }

    fun removeListener(tag: String){
        listeners[tag] = null
    }

    private fun listenersOnResult(result: ProgramsResult){
        listeners.forEach { map ->
            map.value?.onResponse(result)
        }
    }

    private fun listenersOnFailure(t: Throwable){
        listeners.forEach { map ->
            map.value?.onFailure(t)
        }
    }

    private var lastId = "0"

    fun getCurrentProgram(channelId: String): Program?{
        return getPrograms(channelId)?.getCurrentProgram()
    }

    fun getPrograms(channelId: String): Programs?{
        lastId = channelId

        if(liveData.value == null)
            liveData.value = ArrayMap()

        if(liveData.value!![channelId] != null){
            return liveData.value!![channelId]!!.requirePrograms()
        }

        if(!isLoading)
            loadPrograms(channelId)

        return null
    }

    private fun loadPrograms(channelId: String){
        isLoading = true
        serverRequest.request(
            Bundle().apply {
                putString("action", "getProgramsById")
                putString("id", channelId)
            }, this
        )
    }

    override fun onResponse(call: Call<ProgramsResult>, response: Response<ProgramsResult>) {
        isLoading = false
        response.body()?.let{
            it.requirePrograms().sortBy { it.gmtTime }
            liveData.value!![lastId] = it
            listenersOnResult(it)
        }
    }

    override fun onFailure(call: Call<ProgramsResult>, t: Throwable) {
        isLoading = false
        listenersOnFailure(t)
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var programsViewModel: ProgramsViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return programsViewModel as T
        }
    }
}