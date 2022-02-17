package ua.dimaevseenko.format_player.network.request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.result.ClientResult
import ua.dimaevseenko.format_player.network.result.InfoResult

interface RClient {
    @FormUrlEncoded
    @POST(".")
    fun authClient(
        @Field("action") action: String = "auth",
        @Field("login") login: String = Config.Values.login!!,
        @Field("password") password: String = Config.Values.password!!
    ): Call<ClientResult>

    @FormUrlEncoded
    @POST(".")
    fun getClientInfo(
        @Field("action") action: String = "getClientInfo",
        @Field("clientId") clientId: String
    ): Call<InfoResult>
}