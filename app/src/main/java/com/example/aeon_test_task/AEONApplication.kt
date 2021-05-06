package com.example.aeon_test_task

import android.app.Application
import com.example.aeon_test_task.managers.NetManager
import com.example.aeon_test_task.model.Repository
import com.example.aeon_test_task.network.APIService

class AEONApplication: Application() {
    private val netManager by lazy { NetManager(this) }
    private val apiService by lazy { APIService.create() }
    val repository by lazy { Repository(this, netManager, apiService) }
}