package ua.dimaevseenko.format_player.network.request

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.result.LoginResult
import ua.dimaevseenko.format_player.network.result.RegisterResult
import ua.dimaevseenko.format_player.network.result.UnLoginResult

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

    @FormUrlEncoded
    @POST(".")
    fun register(
        @Field("action") action: String = "jadduser",
        @Field("userid") phone: String,
        @Field("username") name: String
    ): Call<RegisterResult>

    @FormUrlEncoded
    @POST(".")
    fun unLogin(
        @Field("action") action: String = "jdeldevice",
        @Field("authmac") authmac: String
    ): Call<UnLoginResult>
}