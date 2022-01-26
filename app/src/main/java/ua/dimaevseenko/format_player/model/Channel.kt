package ua.dimaevseenko.format_player.model

import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.app.Config

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
){
    var imageBitmap: Bitmap? = null
}

class Channels: ArrayList<Channel>(){

    fun setIcons(icons: Icons){
        forEach { channel ->
            icons.forEach { icon ->
                if(channel.id.equals(icon.id)){
                    channel.imageBitmap = Config.Utils.encodeBase64ToBitmap(
                        Base64.decode(icon.base64, Base64.DEFAULT)
                    )
                }
            }
        }
    }
}