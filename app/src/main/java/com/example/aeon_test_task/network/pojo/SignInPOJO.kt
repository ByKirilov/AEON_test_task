package com.example.aeon_test_task.network.pojo

import com.google.gson.annotations.SerializedName

data class SignInPOJO(
    @SerializedName("success")
    val success: String,

    @SerializedName("response")
    val response: Response?,

    @SerializedName("error")
    val error: Error?
)

data class Response(
    @SerializedName("token")
    val token: String
)

