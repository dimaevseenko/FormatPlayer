package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName

data class InfoResult(
    @SerializedName("data")
    val data: Data
){
    data class Data(
        @SerializedName("info")
        val info: Info
    ){
        data class Info(
            @SerializedName("first_name")
            val firstName: String,

            @SerializedName("last_name")
            val lastName: String,

            @SerializedName("patronymic")
            val middleName: String,

            @SerializedName("login")
            val login: String,

            @SerializedName("email")
            val email: String,

            @SerializedName("bonus")
            val bonus: String,

            @SerializedName("tariffName")
            val tariff: String,

            @SerializedName("deposit")
            val deposit: String,

            @SerializedName("address")
            val address: Address
        ){
            data class Address(
                @SerializedName("city")
                val city: String,

                @SerializedName("street")
                val street: String,

                @SerializedName("house")
                val house: String,

                @SerializedName("letter")
                val letter: String,

                @SerializedName("apartment")
                val apartment: String
            )
        }
    }
}