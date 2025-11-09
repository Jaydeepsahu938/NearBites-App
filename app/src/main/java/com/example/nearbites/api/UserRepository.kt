package com.example.nearbites.api

import androidx.lifecycle.LiveData
import com.example.nearbites.database.UserDao
import com.example.nearbites.database.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun getUserByUsername(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }

    suspend fun insertUser(userEntity: UserEntity){
        userDao.insertUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity){
        userDao.updateUser(userEntity)
    }
}