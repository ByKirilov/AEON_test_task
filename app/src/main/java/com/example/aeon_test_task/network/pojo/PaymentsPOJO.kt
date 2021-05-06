package com.example.aeon_test_task.network.pojo

import com.google.gson.annotations.SerializedName
import java.util.*

data class PaymentsPOJO(
        @SerializedName("success")
        val success: String,

        @SerializedName("response")
        val response: List<Payment>?,

        @SerializedName("error")
        val error: Error?
)

data class Payment(
        @SerializedName("desc")
        val description: String?,

        @SerializedName("amount")
        val amount: Double?,

        @SerializedName("currency")
        val currency: String?,

        @SerializedName("created")
        val created: Long?
)
