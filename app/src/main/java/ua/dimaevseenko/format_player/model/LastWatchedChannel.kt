package ua.dimaevseenko.format_player.model

data class LastWatchedChannel(
    val id: String,
    var dateAdded: Long
)

class LastWatchedChannels: ArrayList<LastWatchedChannel>(){

    override fun add(element: LastWatchedChannel): Boolean {
        forEach { lastWatchedChannel ->
            if(lastWatchedChannel.id == element.id) {
                lastWatchedChannel.dateAdded = System.currentTimeMillis()
                return false
            }
        }

        return super.add(element)
    }
}