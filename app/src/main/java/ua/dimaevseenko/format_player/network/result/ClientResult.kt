package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName

data class ClientResult(
    @SerializedName("data")
    val data: Data,
    var info: InfoResult.Data.Info? = null,
    var payments: PaymentsResult.Payments? = null
){
    data class Data(
        @SerializedName("client_id")
        val clientId: String
    )
}
