package ua.dimaevseenko.format_player.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.format_player.model.Auctions
import ua.dimaevseenko.format_player.model.Bonus
import ua.dimaevseenko.format_player.model.Gifts
import ua.dimaevseenko.format_player.network.Server
import javax.inject.Inject

class BonusViewModel @Inject constructor(): ViewModel() {

    private val liveData = MutableLiveData<Bonus>()

    @Inject lateinit var server: Server.Request

    var listener: Server.Listener<Bonus>? = null

    private var isLoading = false

    fun getBonus(): Bonus?{
        return liveData.value
    }

    fun loadBonus(){
        if(!isLoading) {
            isLoading = true
            liveData.value = Bonus()
            server.request(
                Bundle().apply {
                    putString("action", "getBonusGiftItems")
                },
                GiftsCallback()
            )
        }
    }

    private inner class GiftsCallback: Callback<Gifts>{
        override fun onResponse(call: Call<Gifts>, response: Response<Gifts>) {
            isLoading = false
            response.body()?.let { liveData.value!!.gifts = it }
            loadPurchasedGifts()
        }

        override fun onFailure(call: Call<Gifts>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    private fun loadPurchasedGifts(){
        isLoading = true
        server.request(
            Bundle().apply {
                putString("action", "getBonusGiftBuyHistory")
            },
            PurchasedGiftsCallback()
        )
    }

    private inner class PurchasedGiftsCallback: Callback<Gifts>{
        override fun onResponse(call: Call<Gifts>, response: Response<Gifts>) {
            isLoading = false
            response.body()?.let { liveData.value!!.purchasedGifts = it }
            loadAuctions()
        }

        override fun onFailure(call: Call<Gifts>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    private fun loadAuctions(){
        isLoading = true
        server.request(
            Bundle().apply {
                putString("action", "getBonusAuctionItems")
            },
            AuctionsCallback()
        )
    }

    private inner class AuctionsCallback: Callback<Auctions>{
        override fun onResponse(call: Call<Auctions>, response: Response<Auctions>) {
            isLoading = false
            response.body()?.let { liveData.value!!.auctions = it }
            loadBitAuctions()
        }

        override fun onFailure(call: Call<Auctions>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    private fun loadBitAuctions(){
        isLoading = true
        server.request(
            Bundle().apply {
                putString("action", "getBonusAuctionBetHistory")
            },
            BitAuctionsCallback()
        )
    }

    private inner class BitAuctionsCallback: Callback<Auctions>{
        override fun onResponse(call: Call<Auctions>, response: Response<Auctions>) {
            isLoading = false
            response.body()?.let { liveData.value!!.bitAuctions = it }
            listener?.onResponse(liveData.value!!)
        }

        override fun onFailure(call: Call<Auctions>, t: Throwable) {
            isLoading = false
            listener?.onFailure(t)
        }
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject lateinit var bonusViewModel: BonusViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return bonusViewModel as T
        }
    }
}