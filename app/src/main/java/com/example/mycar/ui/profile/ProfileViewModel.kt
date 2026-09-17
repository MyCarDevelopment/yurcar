package com.example.mycar.ui.profile

import android.app.Application
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    private val _fullName = MutableStateFlow(TextFieldValue(sharedPreferences.getString("fullName", "") ?: ""))
    val fullName: StateFlow<TextFieldValue> = _fullName

    private val _email = MutableStateFlow(TextFieldValue(sharedPreferences.getString("email", "") ?: ""))
    val email: StateFlow<TextFieldValue> = _email

    private val _carBrand = MutableStateFlow(TextFieldValue(sharedPreferences.getString("carBrand", "") ?: ""))
    val carBrand: StateFlow<TextFieldValue> = _carBrand

    private val _carModel = MutableStateFlow(TextFieldValue(sharedPreferences.getString("carModel", "") ?: ""))
    val carModel: StateFlow<TextFieldValue> = _carModel

    private val _status = MutableStateFlow(TextFieldValue(sharedPreferences.getString("carStatus", "В продаже авто") ?: "В продаже авто"))
    val carStatus: StateFlow<TextFieldValue> = _status

    private val _igor = MutableStateFlow(TextFieldValue(sharedPreferences.getString("telegram", "") ?: ""))
    val igor: StateFlow<TextFieldValue> = _igor

    fun onFullNameChange(newValue: TextFieldValue) {
        _fullName.value = newValue
    }

    fun onEmailChange(newValue: TextFieldValue) {
        _email.value = newValue
    }

    fun onCarBrandChange(newValue: TextFieldValue) {
        _carBrand.value = newValue
    }

    fun onCarModelChange(newValue: TextFieldValue) {
        _carModel.value = newValue
    }

    fun onCarStatusChange(newValue: TextFieldValue) {
        _status.value = newValue
    }

    fun onIgorChange(newValue: TextFieldValue) {
        _igor.value = newValue
    }

    fun reloadData() {
        _fullName.value = TextFieldValue(sharedPreferences.getString("fullName", "") ?: "")
        _email.value = TextFieldValue(sharedPreferences.getString("email", "") ?: "")
        _carBrand.value = TextFieldValue(sharedPreferences.getString("carBrand", "") ?: "")
        _carModel.value = TextFieldValue(sharedPreferences.getString("carModel", "") ?: "")
        _status.value = TextFieldValue(sharedPreferences.getString("carStatus", "") ?: "")
        _igor.value = TextFieldValue(sharedPreferences.getString("telegram", "") ?: "")
    }

    fun saveData() {
        viewModelScope.launch {
            sharedPreferences.edit().apply {
                putString("fullName", _fullName.value.text)
                putString("email", _email.value.text)
                putString("carBrand", _carBrand.value.text)
                putString("carModel", _carModel.value.text)
                putString("carStatus", _status.value.text)
                putString("telegram", _igor.value.text)
                apply()
            }
        }
    }
}