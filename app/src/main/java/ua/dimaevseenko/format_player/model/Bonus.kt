package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

data class Bonus(
    var gifts: Gifts? = null,
    var purchasedGifts: Gifts? = null,
    var auctions: Auctions? = null,
    var bitAuctions: Auctions? = null
)

data class Gift(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("img")
    val img: String,

    @SerializedName("count")
    val count: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("note")
    val note: String,

    @SerializedName("description")
    val descriptions: String,

    @SerializedName("buy_time")
    val buyTime: String? = null,

    @SerializedName("code")
    val code: String? = null,

    @SerializedName("issue")
    val issue: Issue? = null
){
    data class Issue(
        @SerializedName("issue_date")
        val issueDate: String
    )
}

class Gifts: ArrayList<Gift>()

data class Auction(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("img")
    val img: String,

    @SerializedName("start_price")
    val startPrice: String,

    @SerializedName("description")
    val descriptions: String,

    @SerializedName("note")
    val note: String,

    @SerializedName("timestamp_start")
    val timeStart: String,

    @SerializedName("timestamp_end")
    val timeEnd: Long,

    @SerializedName("current_price")
    val currentPrice: String? = null,

    @SerializedName("issue")
    val issue: Issue? = null
){
    data class Issue(
        @SerializedName("issue_date")
        val issueDate: String
    )
}

class Auctions: ArrayList<Auction>()