package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName

data class PaymentsResult(
    @SerializedName("data")
    val payments: Payments
){
    data class Payment(
        @SerializedName("type")
        val type: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("amount")
        val amount: String,

        @SerializedName("date")
        val date: String,

        @SerializedName("note")
        val note: String?
    )

    class Payments: ArrayList<Payment>()
}