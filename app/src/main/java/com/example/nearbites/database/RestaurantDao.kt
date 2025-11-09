package com.example.nearbites.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant_table")
    fun getAllRestaurant(): List<RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restro: List<RestaurantEntity>)

    @Update
    suspend fun updateRestaurant(restro: RestaurantEntity)
}