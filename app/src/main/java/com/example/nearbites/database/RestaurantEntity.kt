package com.example.nearbites.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table")
data class RestaurantEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val locality:String,
    val city : String,
    val timing : String,
    val lon : String,
    val lat :String,
    val dishes : String,
    val like : String,
    val review : String,
    val rating : String
)
