package com.example.aeon_test_task.network

import com.example.aeon_test_task.network.pojo.PaymentsPOJO
import com.example.aeon_test_task.network.pojo.SignInPOJO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface APIService {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun signIn(@Body loginData: Map<String, String>): SignInPOJO

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    suspend fun payments(@Query("token") token: String): PaymentsPOJO

    companion object Factory {
        fun create(): APIService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://82.202.204.94/api-test/")
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}