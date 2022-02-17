package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName

data class ClientResult(
    @SerializedName("clientId")
    val clientId: String
)