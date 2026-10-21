package com.example.mycar.ui.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

enum class AuthSaveState { IDLE, SAVING, SAVED, ERROR }

class YandexAuthViewModel(application: Application, saved: SavedStateHandle) : AndroidViewModel(application) {
    val nonce: String = saved.get<String>("oauth_state") ?: UUID.randomUUID().toString().also { saved["oauth_state"] = it }
    private val mutableStatus = MutableStateFlow(AuthSaveState.IDLE)
    val status = mutableStatus.asStateFlow()

    fun resetError() { if (mutableStatus.value == AuthSaveState.ERROR) mutableStatus.value = AuthSaveState.IDLE }

    fun complete(result: YandexRedirect.Success) {
        if (mutableStatus.value == AuthSaveState.SAVING || mutableStatus.value == AuthSaveState.SAVED) return
        mutableStatus.value = AuthSaveState.SAVING
        viewModelScope.launch(Dispatchers.IO) {
            val saved = getApplication<Application>().getSharedPreferences("mycar_auth", Context.MODE_PRIVATE).edit()
                .putString("yandex_token", result.token).putBoolean("is_logged_in", true)
                .putLong("auth_time", System.currentTimeMillis())
                .putLong("expires_at", result.expiresInSeconds?.let { System.currentTimeMillis() + it * 1000 } ?: 0L).commit()
            mutableStatus.value = if (saved) AuthSaveState.SAVED else AuthSaveState.ERROR
        }
    }
}
