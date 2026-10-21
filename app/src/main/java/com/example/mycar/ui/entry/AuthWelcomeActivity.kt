package com.example.mycar.ui.entry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycar.LegalDocumentsActivity
import com.example.mycar.ui.login.YandexWebAuthActivity
import com.example.mycar.ui.crash.SosActivity

class AuthWelcomeActivity : AppCompatActivity() {
    private var openingProvider by mutableStateOf(false)
    private val provider = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        openingProvider = false
        if (result.resultCode == Activity.RESULT_OK && SessionRepository(this).current() != null) {
            setResult(Activity.RESULT_OK); finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        openingProvider = savedInstanceState?.getBoolean("opening_provider") ?: false
        setContent {
            AuthWelcomeScreen(onLogin = {
                if (!openingProvider) { openingProvider = true; provider.launch(Intent(this, YandexWebAuthActivity::class.java)) }
            }, onBack = { finish() }, onDocuments = { startActivity(Intent(this, LegalDocumentsActivity::class.java)) },
                onSos = { startActivity(Intent(this, SosActivity::class.java)) }, busy = openingProvider)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("opening_provider", openingProvider)
        super.onSaveInstanceState(outState)
    }
}

@Composable
fun AuthWelcomeScreen(onLogin: () -> Unit, onBack: () -> Unit, onDocuments: () -> Unit,
                      onSos: () -> Unit, busy: Boolean = false) {
    EntryTheme {
        Column(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = EntryAccent) }
                Text("ЮРКАР", color = EntryAccent, fontSize = 14.sp, letterSpacing = 3.sp, fontWeight = FontWeight.Bold)
            }
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Box(Modifier.fillMaxWidth().height(190.dp).background(EntrySurface, RoundedCornerShape(30.dp)), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AccountCircle, null, tint = EntryAccent, modifier = Modifier.size(76.dp))
                        Text("Ваш профиль в Юркаре", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 12.dp))
                    }
                }
                Text("Начнём\nсо знакомства", color = Color.White, fontSize = 34.sp, lineHeight = 39.sp, fontWeight = FontWeight.Bold)
                Text("Войдите через Яндекс ID. Пароль вводится на странице Яндекса — приложение его не запрашивает.",
                    color = EntryMuted, fontSize = 15.sp, lineHeight = 24.sp)
                Surface(color = EntrySurface, shape = RoundedCornerShape(18.dp)) {
                    Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.FactCheck, null, tint = EntryAccent, modifier = Modifier.size(23.dp))
                        Text("После входа покажем документы. Вы сможете прочитать их и отдельно выбрать согласия.", color = EntryMuted,
                            fontSize = 13.sp, lineHeight = 21.sp, modifier = Modifier.padding(start = 12.dp))
                    }
                }
            }
            Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                Button(onClick = onLogin, enabled = !busy, shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFDB00), contentColor = Color.Black),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                    if (busy) CircularProgressIndicator(Modifier.size(20.dp), color = Color.Black, strokeWidth = 2.dp)
                    else { Text("Я", fontWeight = FontWeight.Bold, fontSize = 22.sp); Spacer(Modifier.width(12.dp)); Text("Продолжить с Яндексом", fontWeight = FontWeight.SemiBold) }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(onClick = onDocuments) { Text("Документы", color = EntryMuted, fontSize = 12.sp) }
                    TextButton(onClick = onSos) { Text("Срочная помощь", color = EntryMuted, fontSize = 12.sp) }
                }
            }
        }
    }
}
