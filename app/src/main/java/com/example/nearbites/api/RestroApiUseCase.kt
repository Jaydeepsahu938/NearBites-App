package com.example.nearbites.api

import com.example.nearbites.data.NearbyRestroResponseModel
import com.example.nearbites.database.RestaurantEntity

class RestroApiUseCase(val repository: NearByRestroRepo) {
    suspend fun getNearByRestroList(page: Int, filter: String) = repository.getNearByRestroList(page,filter)
    suspend fun updateRestroInDB(restaurantEntity: RestaurantEntity)  = repository. updateRestroInDB(restaurantEntity)
}