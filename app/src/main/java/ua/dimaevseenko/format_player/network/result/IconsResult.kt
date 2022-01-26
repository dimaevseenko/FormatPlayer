package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Icons

data class IconsResult(
    @SerializedName("channel_icons")
    val icons: Icons,

    @SerializedName("t")
    val timestamp: String
)