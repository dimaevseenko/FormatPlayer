package ua.dimaevseenko.format_player.model

import android.os.Parcel
import android.os.Parcelable
import ua.dimaevseenko.format_player.app.Config

data class Catchup(
    private val channel: Channel,
    private val program: Program
): Stream{

    val allowRewind: Boolean
        get() = channel.rewind == null || channel.rewind == "null"

    constructor(parcel: Parcel) : this(
        parcel.readParcelable<Channel>(ClassLoader.getSystemClassLoader())!!,
        parcel.readParcelable<Program>(ClassLoader.getSystemClassLoader())!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeParcelable(channel, 0)
        dest.writeParcelable(program, 0)
    }

    override fun getStreamId(): String {
        return channel.getStreamId()
    }

    override fun getStreamUrl(): String {
        return Config.SERVER_ADDRESS + "?getcatchup=${getStreamId()}.${program.gmtTime}:${program.gmtTimeTo}"
    }

    override fun getStreamTitle(): String {
        return program.name
    }

    override fun getStreamType(): Type {
        return channel.getStreamType()
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getChannelStream(): Stream{
        return channel
    }

    fun getProgram(): Program{
        return program
    }

    companion object CREATOR : Parcelable.Creator<Catchup> {
        override fun createFromParcel(parcel: Parcel): Catchup {
            return Catchup(parcel)
        }

        override fun newArray(size: Int): Array<Catchup?> {
            return arrayOfNulls(size)
        }
    }
}