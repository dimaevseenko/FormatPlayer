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
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import javax.inject.Inject

class ProgramsViewModel @Inject constructor(): ViewModel(), Callback<ProgramsResult> {

    @Inject lateinit var serverRequest: Server.Request

    private var liveData = MutableLiveData<ArrayMap<String, ProgramsResult>>()

    var listener: Server.Listener<ProgramsResult>? = null

    private var lastId = "0"

    fun getCurrentProgram(channelId: String): Program?{
        return liveData.value?.get(channelId)?.requirePrograms()?.getCurrentProgram()
    }

    fun getPrograms(channelId: String){
        lastId = channelId

        if(liveData.value == null)
            liveData.value = ArrayMap()

        if(liveData.value!![lastId] != null){
            listener?.onResponse(liveData.value!![lastId]!!)
            return
        }

        serverRequest.request(
            Bundle().apply {
                putString("action", "getProgramsById")
                putString("id", lastId)
            }, this
        )
    }

    override fun onResponse(call: Call<ProgramsResult>, response: Response<ProgramsResult>) {
        response.body()?.let{
            it.requirePrograms().sortBy { it.gmtTime }
            liveData.value!![lastId] = it
            listener?.onResponse(it)
        }
    }

    override fun onFailure(call: Call<ProgramsResult>, t: Throwable) {
        listener?.onFailure(t)
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var programsViewModel: ProgramsViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return programsViewModel as T
        }
    }
}