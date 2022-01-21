package ua.dimaevseenko.format_player.fragment.auth

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.network.Server
import javax.inject.Inject

class RequestViewModel<T> @Inject constructor(): ViewModel(), Callback<T> {

    var listener: Server.Listener<T>? = null

    @Inject lateinit var serverRequest: Server.Request

    fun request(bundle: Bundle){
        serverRequest.request(bundle, this)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        response.body()?.let { listener?.onResponse(it) }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        listener?.onFailure(t)
    }

    class Factory<V> @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var viewModel: RequestViewModel<V>

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }
    }
}