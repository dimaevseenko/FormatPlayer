package ua.dimaevseenko.format_player.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.result.LoginResult

interface RUser{
    @FormUrlEncoded
    @POST(".")
    fun login(
        @Field("action") action: String = "jadddevice",
        @Field("authmac") authmac: String,
        @Field("userid") login: String,
        @Field("userpwd") password: String,
        @Field("device") device: String = Config.Device.info
    ): Call<LoginResult>
}