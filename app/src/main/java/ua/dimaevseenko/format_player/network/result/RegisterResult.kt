package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName

data class RegisterResult(
    @SerializedName("status")
    val status: Int,

    @SerializedName("msg")
    val msg: String
)