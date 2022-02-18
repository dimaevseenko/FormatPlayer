package ua.dimaevseenko.format_player.model

data class FavouriteChannel(
    val channelId: String,
    val dateAdded: Long
)

class FavouriteChannels: ArrayList<FavouriteChannel>(){

    fun findFavourite(channelId: String): FavouriteChannel?{
        forEach{ favouriteChannel ->
            if(channelId == favouriteChannel.channelId)
                return favouriteChannel
        }
        return null
    }
}