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
}