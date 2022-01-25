package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Cams
import ua.dimaevseenko.format_player.model.Channels
import ua.dimaevseenko.format_player.model.Genres

data class PlaylistResult(
    @SerializedName("status")
    val status: Int,

    @SerializedName("msg")
    val msg: String,

    @SerializedName("channels")
    val channels: Channels,

    @SerializedName("genres")
    val genres: Genres,

    @SerializedName("cams")
    val cams: Cams
)