package ua.dimaevseenko.format_player.network

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Callback
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.result.LoginResult
import javax.inject.Inject

object Server {

    interface Listener<T>{
        fun onResponse(result: T)
        fun onFailure(t: Throwable)
    }

    class Login @Inject constructor(){

        lateinit var authmac: String

        @Inject lateinit var rUser: RUser

        fun login(login: String, password: String, callback: Callback<LoginResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.login(authmac = authmac, login = login, password = Config.Utils.encodeBase64(password))
                    .enqueue(callback)
            }
        }

        @Inject
        fun inject(context: Context){
            authmac = Config.Device.getUniqueDeviceID(context)
        }
    }
}