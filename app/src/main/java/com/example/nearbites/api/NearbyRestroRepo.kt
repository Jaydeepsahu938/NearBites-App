package com.example.nearbites.api

import com.example.nearbites.data.NearbyRestroResponseModel
import com.example.nearbites.database.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface NearByRestroRepo {
  suspend fun  getNearByRestroList(page: Int,filter: String):Flow<Result<List<RestaurantEntity>>>
  suspend fun  updateRestroInDB(restaurantEntity: RestaurantEntity)
}