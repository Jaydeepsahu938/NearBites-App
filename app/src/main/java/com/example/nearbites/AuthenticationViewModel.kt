package com.example.nearbites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbites.api.UserRepository
import com.example.nearbites.database.UserEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val userRepository: UserRepository): ViewModel() {


    fun checkUserPassword(username: String, enteredPassword: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByUsername(username)
            if(user ==null )
               onResult(0)   // not registered
            else if(user.password == enteredPassword)
                onResult(1)  // authentication done
            else
                onResult(2)   // incorrect password
        }
    }

    fun insertUser(user: UserEntity) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    fun updateUser(user: UserEntity) = viewModelScope.launch {
        userRepository.updateUser(user)
    }

    fun checkPasswordAndConformPasswordMatch(password: String,conformPassWord: String): Boolean{
        if(password==conformPassWord){
            return true
        }
        return false
    }
}