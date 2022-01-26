package ua.dimaevseenko.format_player.model

import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.app.Config

data class Cam(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
){
    var imageBitmap: Bitmap? = null
}

class Cams: ArrayList<Cam>(){

    fun setIcons(icons: Icons){
        forEach { cam ->
            icons.forEach { icon ->
                if(cam.id.equals(icon.id)){
                    cam.imageBitmap = Config.Utils.encodeBase64ToBitmap(
                        Base64.decode(icon.base64, Base64.DEFAULT)
                    )
                }
            }
        }
    }
}