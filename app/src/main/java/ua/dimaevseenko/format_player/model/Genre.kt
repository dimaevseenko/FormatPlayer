package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

class Genres: ArrayList<Genre>(){

    fun findGenre(name: String): Boolean{
        forEach { genre ->
            if(name == genre.name)
                return true
        }
        return false
    }

    fun noEmpty(channels: Channels): Genres{
        val genres = Genres()
        forEach { genre ->
            if(channels.getChannelsForGenre(genre.id).size>0 || genre.id == "-1")
                genres.add(genre)
        }
        return genres
    }
}