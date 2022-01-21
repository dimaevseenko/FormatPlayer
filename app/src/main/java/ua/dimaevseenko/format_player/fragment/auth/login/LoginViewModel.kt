package ua.dimaevseenko.format_player.fragment.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.LoginResult
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel(), Callback<LoginResult> {

    @Inject lateinit var serverLogin: Server.Login

    var listener: Server.Listener<LoginResult>? = null

    fun login(login: String, password: String){
        serverLogin.login(login, password, this)
    }

    override fun onFailure(call: Call<LoginResult>, t: Throwable) {
        listener?.onFailure(t)
    }

    override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
        response.body()?.let { listener?.onResponse(it) }
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var loginViewModel: LoginViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return loginViewModel as T
        }
    }
}