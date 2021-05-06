package com.example.aeon_test_task.model

import android.content.Context
import com.example.aeon_test_task.managers.NetManager
import com.example.aeon_test_task.network.APIService
import com.example.aeon_test_task.network.pojo.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val context: Context, private val netManager: NetManager, private val apiService: APIService) {

    suspend fun signIn(login: String, password: String) : String? {
        netManager.isConnectedToInternet?.let {
            if (it) {
                val loginData = mapOf(
                    "login" to login,
                    "password" to password
                )
                val response = apiService.signIn(loginData)
                if (response.success == "true") {
                    response.response?.let { resp ->
                        setToken(resp.token)
                        return resp.token
                    }
                }
            }
        }
        return null
    }

    suspend fun payments(token: String): List<Payment> {
        netManager.isConnectedToInternet?.let {
            if (it) {
                val response = apiService.payments(token)
                if (response.success == "true") {
                    response.response?.let { resp ->
                        return resp
                    }
                }
            }
        }
        return listOf()
    }

    private fun setToken(token: String) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(TOKEN_TAG, token).apply()
    }

    fun getTokenOrNull(): String? {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getString(TOKEN_TAG, null)
    }

    fun deleteToken() {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(TOKEN_TAG).apply()
    }

    companion object {
        const val TOKEN_TAG = "token_tag"
    }
}