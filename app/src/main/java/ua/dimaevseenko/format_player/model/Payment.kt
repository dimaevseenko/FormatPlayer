package ua.dimaevseenko.format_player.model

import com.google.gson.annotations.SerializedName

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

class Payments: ArrayList<Payment>(){

    fun getPaymentsIn(): Payments{
        return getPaymentsForType(0)
    }

    fun getPaymentsOut(): Payments{
        return getPaymentsForType(1)
    }

    fun getPaymentsForType(type: Int): Payments{
        val payments = Payments()
        forEach{ payment ->
            if(payment.type == type)
                payments.add(payment)
        }
        return payments
    }
}