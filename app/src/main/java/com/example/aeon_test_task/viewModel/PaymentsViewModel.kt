package com.example.aeon_test_task.viewModel

import androidx.lifecycle.*
import com.example.aeon_test_task.model.Repository
import com.example.aeon_test_task.network.pojo.Payment
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PaymentsViewModel(private val repository: Repository) : ViewModel() {

    val payments = MutableLiveData<List<Payment>?>(null)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun getPayments(token: String) = viewModelScope.launch {
        isLoading.value = true
        payments.value = repository.payments(token)
        isLoading.value = false
    }

    fun logout() {
        repository.deleteToken()
    }
}

class PaymentsViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}