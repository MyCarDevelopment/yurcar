package com.example.mycar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mycar.databinding.ActivityMainBinding
import com.example.mycar.ui.entry.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var gateView: ComposeView
    private var pendingStage: String? = null
    private var gateJob: Job? = null
    private var gateError by mutableStateOf<String?>(null)

    private val authentication = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        pendingStage = null
        if (result.resultCode == Activity.RESULT_OK) advanceEntry() else finish()
    }
    private val agreements = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        pendingStage = null
        if (result.resultCode == Activity.RESULT_OK) advanceEntry() else finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        pendingStage = savedInstanceState?.getString("entry_pending")
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root.visibility = View.INVISIBLE
        gateView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { EntryTheme {
                Column(Modifier.fillMaxSize().background(EntryBg).padding(24.dp),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    if (gateError == null) {
                        CircularProgressIndicator(color = EntryAccent)
                        Text("Подготавливаем Юркар…", color = EntryMuted, modifier = Modifier.padding(top = 18.dp))
                    } else {
                        Text(gateError.orEmpty(), color = EntryMuted)
                        Button(onClick = ::advanceEntry, modifier = Modifier.padding(top = 16.dp)) { Text("Повторить") }
                        TextButton(onClick = { finish() }) { Text("Закрыть") }
                    }
                }
            } }
        }
        setContentView(FrameLayout(this).apply {
            addView(binding.root, FrameLayout.LayoutParams(-1, -1))
            addView(gateView, FrameLayout.LayoutParams(-1, -1))
        })
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHost.navController
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.visibility = if (destination.id == R.id.navigation_indicators) View.GONE else View.VISIBLE
        }
        if (pendingStage == null) advanceEntry()
    }

    private fun advanceEntry() {
        if (pendingStage != null) return
        gateJob?.cancel()
        gateError = null
        binding.root.visibility = View.INVISIBLE
        gateView.visibility = View.VISIBLE
        gateJob = lifecycleScope.launch {
            val session = SessionRepository(this@MainActivity).current()
            if (session == null) {
                lifecycle.withStarted {
                    pendingStage = "auth"
                    authentication.launch(Intent(this@MainActivity, AuthWelcomeActivity::class.java))
                }
                return@launch
            }
            val current = try { ConsentRepository(this@MainActivity).isCurrent(session) }
            catch (_: IOException) { gateError = "Не удалось проверить документы. Попробуйте ещё раз."; return@launch }
            if (!current) {
                lifecycle.withStarted {
                    pendingStage = "consents"
                    agreements.launch(Intent(this@MainActivity, AgreementActivity::class.java))
                }
                return@launch
            }
            lifecycle.withStarted {
                if (SessionRepository(this@MainActivity).current()?.subjectKey != session.subjectKey) {
                    advanceEntry()
                } else {
                    FirebaseAnalytics.getInstance(this@MainActivity)
                    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
                    binding.root.visibility = View.VISIBLE
                    gateView.visibility = View.GONE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (::binding.isInitialized && binding.root.visibility == View.VISIBLE && SessionRepository(this).current() == null) advanceEntry()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("entry_pending", pendingStage)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp() || super.onSupportNavigateUp()
}

abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        val config = Configuration(newBase?.resources?.configuration)
        config.fontScale = 0.9f
        applyOverrideConfiguration(config)
        super.attachBaseContext(newBase)
    }
    fun createComposeView(content: @Composable () -> Unit): ComposeView = ComposeView(this).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { content() }
    }
}
