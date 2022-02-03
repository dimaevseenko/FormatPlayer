package ua.dimaevseenko.format_player.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class Program(
    @SerializedName("name")
    val name: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("time_to")
    val endTime: String,

    @SerializedName("gmt_time")
    val gmtTime: Long,

    @SerializedName("gmt_time_to")
    val gmtTimeTo: Long,

    @SerializedName("category")
    val category: String
): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(time)
        dest?.writeString(endTime)
        dest?.writeLong(gmtTime)
        dest?.writeLong(gmtTimeTo)
        dest?.writeString(category)
    }

    companion object CREATOR : Parcelable.Creator<Program> {
        override fun createFromParcel(parcel: Parcel): Program {
            return Program(parcel)
        }

        override fun newArray(size: Int): Array<Program?> {
            return arrayOfNulls(size)
        }
    }

    private fun formatDate(pattern: String): String{
        return SimpleDateFormat(pattern).format(
            Date(gmtTime*1000)
        )
    }

    fun getDay(): String{
        return formatDate("dd.MM.yyyy")
    }

    fun getTimeStart(): String{
        return formatDate("HH:mm")
    }
}

class Programs: ArrayList<Program>(){

    fun getCurrentProgramId(): Long{
        return getCurrentProgram().gmtTime
    }

    fun getCurrentProgram(): Program{
        return get(getCurrentProgramPosition())
    }

    fun getCurrentProgramPosition(): Int{
        var index = 0
        forEach { program ->
            if(System.currentTimeMillis() < (program.gmtTime*1000))
                return index-1
            index++
        }
        return index
    }
}