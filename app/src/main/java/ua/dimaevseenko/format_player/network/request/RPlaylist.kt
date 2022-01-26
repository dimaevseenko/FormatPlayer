package ua.dimaevseenko.format_player.network.request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ua.dimaevseenko.format_player.BuildConfig
import ua.dimaevseenko.format_player.network.result.IconsResult
import ua.dimaevseenko.format_player.network.result.PlaylistResult

interface RPlaylist{
    @FormUrlEncoded
    @POST(".")
    fun getPlaylist(
        @Field("action") action: String = "jgetchannellist",
        @Field("authmac") authmac: String,
        @Field("version") version: String = BuildConfig.VERSION_NAME
    ): Call<PlaylistResult>

    @GET(".")
    fun getIcons(
        @Query("jgetjsoniconschannels")
        iconsModificationTime: Int = 0
    ): Call<IconsResult>
}