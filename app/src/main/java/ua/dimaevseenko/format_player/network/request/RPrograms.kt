package ua.dimaevseenko.format_player.network.request

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.network.result.ProgramsResult

interface RPrograms {
    @FormUrlEncoded
    @POST(".")
    fun getPrograms(
        @Field("action") action: String = "getProgramsById",
        @Field("id") id: String
    ): Call<ProgramsResult>
}