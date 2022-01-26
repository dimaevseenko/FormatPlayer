package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("id")
    val id: String,

    @SerializedName("base64")
    val base64: String
)

class Icons: ArrayList<Icon>()