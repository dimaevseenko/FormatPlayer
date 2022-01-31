package ua.dimaevseenko.format_player.model

import android.os.Parcelable

interface Stream: Parcelable{
    fun getStreamUrl(): String
    fun getStreamTitle(): String
    fun getStreamName(): String?
}