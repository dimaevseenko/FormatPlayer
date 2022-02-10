package ua.dimaevseenko.format_player.model

import android.os.Parcelable

interface Stream: Parcelable{
    fun getStreamId(): String
    fun getStreamUrl(): String
    fun getStreamTitle(): String
    fun getQualityType(): Quality
}

enum class Quality{
    RADIO, SD, HD, AUTO
}