package com.example.nearbites.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class,RestaurantEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        const val DATABASE_NAME = "near_bites_db"

        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                                context.applicationContext,
                                AppDataBase::class.java,
                                DATABASE_NAME
                            ).fallbackToDestructiveMigration(false)
                    .build().also { instance = it }
            }
        }
    }
}