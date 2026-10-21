package com.example.mycar.ui.entry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

data class ConsentFormState(val documents: List<DocumentSnapshot> = emptyList(), val loading: Boolean = true,
                            val saving: Boolean = false, val saved: Boolean = false, val error: String? = null)

class ConsentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ConsentRepository(application)
    private val sessionRepository = SessionRepository(application)
    private val mutableState = MutableStateFlow(ConsentFormState())
    val state = mutableState.asStateFlow()
    private var loadJob: Job? = null

    init { load() }

    fun load() {
        if (mutableState.value.saving) return
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            mutableState.value = mutableState.value.copy(loading = true, error = null)
            try {
                val session = sessionRepository.current()
                if (session != null && repository.isCurrent(session)) {
                    mutableState.value = mutableState.value.copy(loading = false, saved = true)
                } else mutableState.value = ConsentFormState(documents = repository.documents(), loading = false)
            } catch (_: IOException) {
                mutableState.value = mutableState.value.copy(loading = false, error = "Не удалось загрузить документы. Попробуйте ещё раз.")
            }
        }
    }

    fun confirm(choices: Set<String>) {
        val current = mutableState.value
        if (current.saving || current.loading || !requiredConsentsSelected(choices)) return
        val session = sessionRepository.current()
        if (session == null) {
            mutableState.value = current.copy(error = "Вход больше не активен. Вернитесь к авторизации.")
            return
        }
        mutableState.value = current.copy(saving = true, error = null)
        viewModelScope.launch {
            try {
                repository.save(session, choices, current.documents)
                mutableState.value = mutableState.value.copy(saving = false, saved = true)
            } catch (_: IOException) {
                mutableState.value = mutableState.value.copy(saving = false, error = "Не удалось сохранить подтверждения. Проверьте свободное место и повторите попытку.")
            }
        }
    }
}
