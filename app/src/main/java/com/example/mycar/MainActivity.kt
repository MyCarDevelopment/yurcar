package com.example.mycar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mycar.databinding.ActivityMainBinding
import com.example.mycar.ui.login.YandexWebAuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var navController: NavController
    private val AUTH_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // NavHost
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        supportActionBar?.hide()

        // 🔥 ПРОВЕРКА АВТОРИЗАЦИИ
        binding.navView.visibility = View.VISIBLE  // Покажем UI сразу

        // Проверяем авторизацию ПОСЛЕ полной инициализации
        lifecycleScope.launch {
            delay(100)  // Дать время onActivityResult
            checkAuthStatus()
        }
        // Navigation setup
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_crash
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_indicators -> binding.navView.visibility = android.view.View.GONE
                else -> binding.navView.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun checkAuthStatus() {
        val prefs = getSharedPreferences("mycar_auth", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("is_logged_in", false)
        val token = prefs.getString("yandex_token", "") ?: ""

        Log.d("MainActivity", "🔥 CHECK: isLoggedIn=$isLoggedIn, token=${token.take(10)}...")

        if (!isLoggedIn || token.isEmpty()) {
            Log.d("MainActivity", "→ Запуск авторизации")
            binding.navView.visibility = View.GONE
            startActivityForResult(Intent(this, YandexWebAuthActivity::class.java), AUTH_REQUEST_CODE)
        } else {
            Log.d("MainActivity", "✅ Уже авторизован")
            binding.navView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "🔥 RESULT: code=$requestCode, result=$resultCode")

        if (requestCode == AUTH_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d("MainActivity", "✅ SUCCESS из WebAuth")
                    binding.navView.visibility = View.VISIBLE
                    checkAuthStatus()  // 🔥 ПЕРЕПРОВЕРИМ SharedPreferences!
                    Toast.makeText(this, "✅ Добро пожаловать в Юркар!", Toast.LENGTH_LONG).show()
                }
                Activity.RESULT_CANCELED -> {
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        val newConfig = Configuration(newBase?.resources?.configuration)
        newConfig.fontScale = 0.9f
        applyOverrideConfiguration(newConfig)
        super.attachBaseContext(newBase)
    }

    // Удобный метод для Compose фрагментов
    fun createComposeView(content: @Composable () -> Unit): ComposeView {
        return ComposeView(this).apply {
            setContent { content() }
        }
    }
}