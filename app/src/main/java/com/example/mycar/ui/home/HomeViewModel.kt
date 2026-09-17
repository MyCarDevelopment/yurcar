package com.example.mycar.ui.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CarData(val make: String, val model: String)
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("car_prefs", Context.MODE_PRIVATE)
    private val _carData = MutableLiveData<CarData?>()

    val carData: LiveData<CarData?> = _carData

    init {
        loadCarData()
    }

    fun saveCarData(make: String, model: String) {
        sharedPreferences.edit()
            .putString("car_make", make)
            .putString("car_model", model)
            .apply()
        _carData.value = CarData(make, model)
    }

    private fun loadCarData() {
        val make = sharedPreferences.getString("car_make", null)
        val model = sharedPreferences.getString("car_model", null)
        if (make != null && model != null) {
            _carData.value = CarData(make, model)
        } else {
            _carData.value = null
        }
    }
}




