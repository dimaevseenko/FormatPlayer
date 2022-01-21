package ua.dimaevseenko.format_player.network

import android.content.Context
import android.os.Bundle
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.request.RUser
import ua.dimaevseenko.format_player.network.result.LoginResult
import ua.dimaevseenko.format_player.network.result.RegisterResult
import javax.inject.Inject

object Server {

    interface Listener<T>{
        fun onResponse(result: T)
        fun onFailure(t: Throwable)
    }

    class Request @Inject constructor(){

        @Inject lateinit var login: Lazy<Login>
        @Inject lateinit var register: Lazy<Register>

        fun<T> request(bundle: Bundle, callback: Callback<T>){
            when(bundle.getString("action")){
                "jadddevice"->{ login.get().login(bundle, callback = callback as Callback<LoginResult>) }
                "jadduser" -> { register.get().register(bundle, callback = callback as Callback<RegisterResult>) }
            }
        }
    }

    class Login @Inject constructor(){
        private lateinit var authmac: String
        @Inject lateinit var rUser: RUser

        fun login(bundle: Bundle, callback: Callback<LoginResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.login(authmac = authmac, login = bundle.getString("login")!!, password = Config.Utils.encodeBase64(bundle.getString("password")!!))
                    .enqueue(callback)
            }
        }

        @Inject
        fun inject(context: Context){
            authmac = Config.Device.getUniqueDeviceID(context)
        }
    }

    class Register @Inject constructor(){
        @Inject lateinit var rUser: RUser

        fun register(bundle: Bundle,  callback: Callback<RegisterResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.register(phone = bundle.getString("phone")!!, name = bundle.getString("name")!!).enqueue(callback)
            }
        }
    }
}