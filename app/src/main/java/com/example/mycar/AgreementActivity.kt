package com.example.mycar

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import com.example.mycar.ui.entry.*

class AgreementActivity : AppCompatActivity() {
    private val model: ConsentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        if (SessionRepository(this).current() == null) { finish(); return }
        setContent {
            val state by model.state.collectAsState()
            LaunchedEffect(state.saved) {
                if (state.saved) { setResult(Activity.RESULT_OK); finish() }
            }
            ConsentScreen(state.documents, state.loading, state.saving, state.error,
                onRetry = model::load, onConfirm = model::confirm, onBack = { finish() })
        }
    }
}
