package ua.dimaevseenko.format_player.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
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
    val rewind: String?,

    @SerializedName("server_time")
    val serverTime: String
): Stream{
    var imageBitmap: Bitmap? = null

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
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
        parcel.writeString(genreId)
        parcel.writeString(type)
        parcel.writeString(catchup)
        parcel.writeString(rewind)
        parcel.writeString(serverTime)
        parcel.writeParcelable(imageBitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
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
        return if(type == "sd")
            Quality.SD
        else if (type == "hd")
            Quality.HD
        else
            Quality.RADIO
    }
}

class Channels: ArrayList<Channel>(){

    fun setIcons(icons: Icons){
        forEach { channel ->
            icons.forEach { icon ->
                if(channel.id == icon.id){
                    channel.imageBitmap = Config.Utils.encodeBase64ToBitmap(
                        Base64.decode(icon.base64, Base64.DEFAULT)
                    )
                }
            }
        }
    }

    fun getChannelsForGenre(genreId: String): Channels{
        if(genreId == "-1")
            return getLastWatched()
        else if(genreId == "0")
            return this

        val channels = Channels()
        forEach { channel ->
            if(channel.genreId == genreId)
                channels.add(channel)
        }
        return channels
    }

    private fun getLastWatched(): Channels{
        val channels = Channels()
        Config.Values.lastWatchedChannelsIds.forEach { last ->
            this.forEach { channel ->
                if(last.id == channel.id)
                    channels.add(channel)
            }
        }

        channels.reverse()
        return channels
    }
}