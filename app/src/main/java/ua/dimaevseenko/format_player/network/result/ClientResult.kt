package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Info
import ua.dimaevseenko.format_player.model.Payments

data class ClientResult(
    @SerializedName("data")
    val data: Data,
    var info: Info? = null,
    var payments: Payments? = null
){
    data class Data(
        @SerializedName("client_id")
        val clientId: String
    )
}
