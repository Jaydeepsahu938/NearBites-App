package com.example.nearbites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nearbites.api.NearbyRestroRepoImpl
import com.example.nearbites.api.RestroApiUseCase
import com.example.nearbites.data.NearbyRestroResponseModel
import com.example.nearbites.database.RestaurantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.Result

class MainViewModel(val restroApiUseCase: RestroApiUseCase) : ViewModel() {

    init {
        getNearByRestroList()
    }
    private var isLoading = false
    private var currentPage = 1


    private var _restroData = MutableLiveData<Result<List<RestaurantEntity>>>()
    val restroData : LiveData<Result<List<RestaurantEntity>>> get() = _restroData

    fun getNearByRestroList(){
             if (isLoading) return
             isLoading = true
             viewModelScope.launch(Dispatchers.IO){
                 try {
                     restroApiUseCase.getNearByRestroList(currentPage++,createFilter(28.4795324, 76.9937197)).collectLatest {
                         _restroData.postValue(it)
                     }
                 } catch (e: Exception) {
                    _restroData.postValue(Result.failure(e))
                 }finally {
                     isLoading = false
                 }
             }
    }

    fun updateRestaurant(restaurantEntity: RestaurantEntity){
        viewModelScope.launch(Dispatchers.IO){
            try{
                restroApiUseCase.updateRestroInDB(restaurantEntity)
            }catch (e: Exception){
                print("$e")
            }
        }
    }

    fun createFilter(lat: Double, lon: Double, radius: Int = 20000): String {
        return "circle:$lon,$lat,$radius"
    }
}