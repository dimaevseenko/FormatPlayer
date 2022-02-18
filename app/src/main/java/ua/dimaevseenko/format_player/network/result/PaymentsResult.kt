package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Payments

data class PaymentsResult(
    @SerializedName("data")
    val payments: Payments
)