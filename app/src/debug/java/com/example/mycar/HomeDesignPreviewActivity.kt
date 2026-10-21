package com.example.mycar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import com.example.mycar.ui.home.HomeScreenContent
import com.example.mycar.ui.mycarfragment.*
import com.example.mycar.ui.indicators.IndicatorsActivity
import com.example.mycar.ui.instructions.InstructionsActivity
import com.example.mycar.ui.entry.*
import androidx.compose.runtime.*

/** Debug-only entry point for reviewing the home screen without sign-in or changing agreements. */
class HomeDesignPreviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(16, 18, 20)
        window.navigationBarColor = android.graphics.Color.rgb(16, 18, 20)
        val preview = intent.getStringExtra("screen")
        if (preview in listOf("onboarding", "welcome", "consents")) configureEntryWindow(this)
        setContent {
            val screen = intent.getStringExtra("screen")
            val section = GuideSection.entries.firstOrNull { it.name.equals(screen, ignoreCase = true) }
            when {
                screen == "onboarding" -> OnboardingScreen(
                    onComplete = { startActivity(Intent(this, MainActivity::class.java)) },
                    onSos = { startActivity(Intent(this, com.example.mycar.ui.crash.SosActivity::class.java)) },
                    initialPage = intent.getIntExtra("page", 0))
                screen == "welcome" -> AuthWelcomeScreen(
                    onLogin = { startActivity(Intent(this, com.example.mycar.ui.login.YandexWebAuthActivity::class.java)) },
                    onBack = { finish() }, onDocuments = { startActivity(Intent(this, LegalDocumentsActivity::class.java)) },
                    onSos = { startActivity(Intent(this, com.example.mycar.ui.crash.SosActivity::class.java)) })
                screen == "consents" -> {
                    val documents by produceState(emptyList<DocumentSnapshot>()) { value = ConsentRepository(this@HomeDesignPreviewActivity).documents() }
                    ConsentScreen(documents, documents.isEmpty(), false, null, onRetry = {},
                        onConfirm = { startActivity(Intent(this, HomeDesignPreviewActivity::class.java)) }, onBack = { finish() })
                }
                screen == "sos" -> com.example.mycar.ui.crash.SosScreen(onDial = {}, onSource = {},
                    initialTopic = intent.getStringExtra("topic"))
                section != null -> DriverGuideSection(section, onBack = { finish() }, initialArticleId = intent.getStringExtra("article"))
                screen == "indicators" -> com.example.mycar.ui.indicators.NavigationComponentt(onBack = { finish() })
                screen == "products" -> com.example.mycar.ui.marketplace.ProductsScreen(onBack = { finish() })
                screen == "tips" -> UsefulTipsScreen(onBack = { finish() })
                screen == "guide" -> MyCarScreen(
                    { startActivity(Intent(this, SymptomsActivity::class.java)) },
                    { startActivity(Intent(this, LocationActivity::class.java)) },
                    { startActivity(Intent(this, IndicatorsActivity::class.java)) },
                    { startActivity(Intent(this, CriticalityActivity::class.java)) },
                    { startActivity(Intent(this, InstructionsActivity::class.java)) },
                    { startActivity(Intent(this, AccidentActivity::class.java)) }, onBack = { finish() },
                    onTipsClick = { startActivity(Intent(this, UsefulTipsActivity::class.java)) })
                else -> HomeScreenContent(emptyMap(), {
                    val target = when (it) {
                        com.example.mycar.ui.CardType.BUY_CAR -> MyCarActivity::class.java
                        com.example.mycar.ui.CardType.TIRES -> IndicatorsActivity::class.java
                        com.example.mycar.ui.CardType.INSTRUCTIONS -> InstructionsActivity::class.java
                        com.example.mycar.ui.CardType.SIXTH -> com.example.mycar.ui.marketplace.MarketplaceFragment::class.java
                        com.example.mycar.ui.CardType.SERVICES -> com.example.mycar.ui.services.ServicesActivity::class.java
                        com.example.mycar.ui.CardType.SELL_CAR -> com.example.mycar.ui.checkfines.FinesFragment::class.java
                    }
                    startActivity(Intent(this, target))
                }, {}, {})
            }
        }
    }
}
