package com.example.mycar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.mycar.ui.entry.OnboardingScreen
import com.example.mycar.ui.entry.configureEntryWindow
import com.example.mycar.ui.crash.SosActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        val prefs = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        if (!prefs.getBoolean("isFirstLaunch", true)) {
            openApp()
            return
        }
        setContent {
            OnboardingScreen(onComplete = {
                prefs.edit().putBoolean("isFirstLaunch", false).putInt("introduction_version", 1).apply()
                openApp()
            }, onSos = { startActivity(Intent(this, SosActivity::class.java)) })
        }
    }

    private fun openApp() {
        if (isFinishing) return
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
