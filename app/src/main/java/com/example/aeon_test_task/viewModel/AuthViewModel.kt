package com.example.aeon_test_task.viewModel

import androidx.lifecycle.*
import com.example.aeon_test_task.Event
import com.example.aeon_test_task.model.Repository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _token = MutableLiveData<Event<String?>>(null)
    val token: LiveData<Event<String?>>
        get() = _token

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    fun showWrongUserData() {
        _showSnackBar.value = Event(WRONG_USER_DATA)
    }

    fun signIn(login: String?, password: String?) = viewModelScope.launch {
        if (login != null && password != null && login.isNotEmpty() && password.isNotEmpty()) {
            val loginResult = repository.signIn(login, password)
            if (loginResult != null) {
                _token.value = Event(loginResult)
            }
            else {
                showWrongUserData()
            }
        }
        else {
            showWrongUserData()
        }
    }

    fun getToken() {
        _token.value = Event(repository.getTokenOrNull())
    }

    companion object {
        private const val WRONG_USER_DATA = "Неверный логин или пароль"
    }
}

class AuthViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}