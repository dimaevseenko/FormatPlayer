package ua.dimaevseenko.format_player.network.request

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.BuildConfig
import ua.dimaevseenko.format_player.network.result.PlaylistResult

interface RPlaylist{
    @FormUrlEncoded
    @POST(".")
    fun getPlaylist(
        @Field("action") action: String = "jgetchannellist",
        @Field("authmac") authmac: String,
        @Field("version") version: String = BuildConfig.VERSION_NAME
    ): Call<PlaylistResult>
}