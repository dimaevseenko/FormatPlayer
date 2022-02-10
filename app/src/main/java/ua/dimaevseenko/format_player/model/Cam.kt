package ua.dimaevseenko.format_player.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
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
): Stream{
    var imageBitmap: Bitmap? = null

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        imageBitmap = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeParcelable(imageBitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cam> {
        override fun createFromParcel(parcel: Parcel): Cam {
            return Cam(parcel)
        }

        override fun newArray(size: Int): Array<Cam?> {
            return arrayOfNulls(size)
        }
    }

    override fun getStreamId(): String {
        return id
    }

    override fun getStreamUrl(): String {
        return url
    }

    override fun getStreamTitle(): String {
        return name
    }

    override fun getQualityType(): Quality {
        return Quality.HD
    }
}

class Cams: ArrayList<Cam>(){

    fun setIcons(icons: Icons){
        forEach { cam ->
            icons.forEach { icon ->
                if(cam.id == icon.id){
                    cam.imageBitmap = Config.Utils.encodeBase64ToBitmap(
                        Base64.decode(icon.base64, Base64.DEFAULT)
                    )
                }
            }
        }
    }
}