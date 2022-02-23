package ua.dimaevseenko.format_player.network.request

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.model.Auctions
import ua.dimaevseenko.format_player.model.Gifts

interface RBonus{
    @FormUrlEncoded
    @POST(".")
    fun getBonusGiftItems(
        @Field("action") action: String = "getBonusGiftItems"
    ): Call<Gifts>

    @FormUrlEncoded
    @POST(".")
    fun getBonusGiftBuyHistory(
        @Field("action") action: String = "getBonusGiftBuyHistory",
        @Field("login") login: String = Config.Values.login!!
    ): Call<Gifts>

    @FormUrlEncoded
    @POST(".")
    fun getBonusAuctionItems(
        @Field("action") action: String = "getBonusAuctionItems"
    ): Call<Auctions>

    @FormUrlEncoded
    @POST(".")
    fun getBonusAuctionBetHistory(
        @Field("action") action: String = "getBonusAuctionBetHistory",
        @Field("login") login: String = Config.Values.login!!
    ): Call<Auctions>
}