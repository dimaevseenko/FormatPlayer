package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

data class Cam(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

class Cams: ArrayList<Cam>()