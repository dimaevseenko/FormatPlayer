package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("genre_id")
    val genreId: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("catchup")
    val catchup: String,

    @SerializedName("rewind")
    val rewind: String,

    @SerializedName("server_time")
    val serverTime: String
)

class Channels: ArrayList<Channel>()