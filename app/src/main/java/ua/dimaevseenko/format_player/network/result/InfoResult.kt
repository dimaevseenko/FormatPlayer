package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Info

data class InfoResult(
    @SerializedName("data")
    val data: Data
){
    data class Data(
        @SerializedName("info")
        val info: Info
    )
}