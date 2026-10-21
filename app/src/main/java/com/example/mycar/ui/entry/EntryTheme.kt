package com.example.mycar.ui.entry

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat

val EntryBg = Color(0xFF101214)
val EntrySurface = Color(0xFF1B1E22)
val EntryMuted = Color(0xFFA2A8B0)
val EntryAccent = Color(0xFFFFA666)

fun configureEntryWindow(activity: Activity) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    activity.window.statusBarColor = android.graphics.Color.TRANSPARENT
    activity.window.navigationBarColor = android.graphics.Color.rgb(16, 18, 20)
    WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
        isAppearanceLightStatusBars = false
        isAppearanceLightNavigationBars = false
    }
}

@Composable
fun EntryTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = darkColors(primary = EntryAccent, secondary = EntryAccent,
        onPrimary = EntryBg, onSecondary = EntryBg, background = EntryBg, surface = EntrySurface), content = content)
}
