package com.example.nearbites.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nearbites.AuthenticationViewModel

class AuthenticationViewModelFactory(
    private val repository: UserRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthenticationViewModel(repository) as T
    }
}