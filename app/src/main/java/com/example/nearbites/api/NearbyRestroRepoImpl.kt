package com.example.nearbites.api

import androidx.lifecycle.LiveData
import com.example.nearbites.data.NearbyRestroResponseModel
import com.example.nearbites.database.RestaurantDao
import com.example.nearbites.database.RestaurantEntity
import com.example.nearbites.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NearbyRestroRepoImpl(val restaurantDao: RestaurantDao) : NearByRestroRepo {
    val apiService = RetrofitClient.apiService


    override suspend fun getNearByRestroList(page: Int,filter: String): Flow<Result<List<RestaurantEntity>>> = flow {
        val limit = 20
        val offset = page * limit
        val response= apiService.getRestroList(filter = filter, limit = limit, offset = offset, apiKey = "c42c50d8721147109535a6e68902b5bc")
        insertRestroListToDb(response)
        emit(Result.success(restaurantDao.getAllRestaurant()))
    }

     suspend fun insertRestroListToDb(result: NearbyRestroResponseModel) {
         val mappedProfiles = result.features.map {
             RestaurantEntity(
                 id = it.properties.name,
                 name = it.properties.name,
                 locality = it.properties.street,
                 city = it.properties.city?:"",
                 timing = it.properties.datasource.raw.openingHours?:"24/7",
                 lon = it.properties.lon.toString(),
                 lat = it.properties.lat.toString(),
                 dishes = it.properties.catering?.cuisine ?: "",
                 like ="",
                 review = "",
                 rating = (it.properties.catering?.stars?:4).toString()
             )
         }
         restaurantDao.insertRestaurant(mappedProfiles)
     }

    override suspend fun updateRestroInDB(restro: RestaurantEntity) {
            restaurantDao.updateRestaurant(restro)
    }

}