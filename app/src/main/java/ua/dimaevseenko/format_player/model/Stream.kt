package ua.dimaevseenko.format_player.model

import android.os.Parcelable

interface Stream: Parcelable{
    fun getStreamId(): String
    fun getStreamUrl(): String
    fun getStreamTitle(): String
    fun getStreamType(): Type
}

enum class Type{
    RADIO, SD, HD, CAMERA
}

enum class Quality{
    LOW, MID, SD, HD, FULL_HD, AUTO
}