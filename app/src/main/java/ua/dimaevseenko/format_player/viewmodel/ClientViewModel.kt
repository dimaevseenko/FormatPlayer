package ua.dimaevseenko.format_player.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ClientResult
import ua.dimaevseenko.format_player.network.result.InfoResult
import ua.dimaevseenko.format_player.network.result.PaymentsResult
import javax.inject.Inject

class ClientViewModel @Inject constructor(): ViewModel(){

    @Inject lateinit var request: Server.Request

    var listener: Server.Listener<ClientResult>? = null

    private var clientLiveData = MutableLiveData<ClientResult>()

    private var isLoading = false

    fun clearClient(){
        clientLiveData.value = null
    }

    fun getClient(): ClientResult?{
        if(clientLiveData.value == null)
            if(!isLoading)
                loadClient()

        return clientLiveData.value
    }

    fun loadClient(){
        isLoading = true
        request.request(
            Bundle().apply {
                putString("action", "authClient")
            },
            ClientAuth()
        )
    }

    private inner class ClientAuth: Callback<ClientResult>{
        override fun onResponse(call: Call<ClientResult>, response: Response<ClientResult>) {
            isLoading = false
            clientLiveData.value = response.body()
            loadInfo()
        }

        override fun onFailure(call: Call<ClientResult>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    private fun loadInfo(){
        isLoading = true
        request.request(
            Bundle().apply {
                putString("action", "getClientInfo")
                putString("clientId", clientLiveData.value?.data?.clientId)
            },
            ClientInfo()
        )
    }

    private inner class ClientInfo: Callback<InfoResult>{
        override fun onResponse(call: Call<InfoResult>, response: Response<InfoResult>) {
            isLoading = false
            clientLiveData.value?.info = response.body()?.data?.info
            loadPayments()
        }

        override fun onFailure(call: Call<InfoResult>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    private fun loadPayments(){
        isLoading = true
        request.request(
            Bundle().apply {
                putString("action", "getClientPayments")
                putString("clientId", clientLiveData.value?.data?.clientId)
            },
            ClientPayments()
        )
    }

    private inner class ClientPayments: Callback<PaymentsResult>{
        override fun onResponse(call: Call<PaymentsResult>, response: Response<PaymentsResult>) {
            isLoading = false
            clientLiveData.value?.payments = response.body()?.payments
            clientLiveData.value?.let { listener?.onResponse(it) }
        }

        override fun onFailure(call: Call<PaymentsResult>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var clientViewModel: ClientViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return clientViewModel as T
        }
    }
}