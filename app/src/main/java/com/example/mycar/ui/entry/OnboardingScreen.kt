package com.example.mycar.ui.entry

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private data class WelcomePage(val label: String, val title: String, val body: String,
                               val icon: ImageVector, val tint: Color, val tags: List<String>)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onComplete: () -> Unit, onSos: () -> Unit, initialPage: Int = 0) {
    val pages = remember { listOf(
        WelcomePage("РАЗОБРАТЬСЯ", "Ваш автомобиль.\nПонятным языком.",
            "Откройте справочник и найдите подсказку по симптому, узлу автомобиля или значку на панели. Внутри — понятные действия и советы по уходу.",
            Icons.Default.AutoStories, EntryAccent, listOf("Симптомы", "Индикаторы", "Обслуживание")),
        WelcomePage("НАЙТИ РЯДОМ", "Нужное место —\nна карте.",
            "На карте Петербурга выберите категорию, найдите сервис по названию или адресу и откройте его карточку. Маршрут продолжится в картах или навигаторе.",
            Icons.Default.Explore, Color(0xFF9BD5C0), listOf("СТО", "Шиномонтаж", "Кузовной ремонт")),
        WelcomePage("БЫТЬ ГОТОВЫМ", "В дороге\nесть план.",
            "Во вкладке SOS собраны первые шаги при ДТП, поломке и эвакуации. Новости и сообщество — на главной, полезные советы — в справочнике.",
            Icons.Default.HealthAndSafety, Color(0xFFB7C6FF), listOf("Памятки SOS", "Сообщество", "Советы"))
    ) }
    val pager = rememberPagerState(initialPage = initialPage.coerceIn(0, 2)) { pages.size }
    val scope = rememberCoroutineScope()
    BackHandler(enabled = pager.currentPage > 0) { scope.launch { pager.animateScrollToPage(pager.currentPage - 1) } }
    EntryTheme {
        Column(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
            Row(Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 22.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("ЮРКАР", color = EntryAccent, fontSize = 15.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Spacer(Modifier.weight(1f))
                if (pager.currentPage < 2) TextButton(onClick = onComplete) { Text("Пропустить", color = EntryMuted, fontSize = 12.sp) }
            }
            HorizontalPager(state = pager, modifier = Modifier.weight(1f).fillMaxWidth()) { index ->
                val page = pages[index]
                Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    WelcomeIllustration(index, page.icon, page.tint)
                    Column {
                        Text(page.label, color = page.tint, fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
                        Text(page.title, color = Color.White, fontSize = 34.sp, lineHeight = 39.sp,
                            fontWeight = FontWeight.Bold, letterSpacing = (-.8).sp, modifier = Modifier.padding(top = 12.dp))
                        Text(page.body, color = EntryMuted, fontSize = 15.sp, lineHeight = 24.sp, modifier = Modifier.padding(top = 16.dp))
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        page.tags.forEach { tag ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, null, tint = page.tint, modifier = Modifier.size(16.dp))
                                Text(tag, color = Color(0xFFD6DADF), fontSize = 13.sp, modifier = Modifier.padding(start = 9.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                }
            }
            Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                Row(Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    pages.indices.forEach { index -> Box(Modifier.padding(horizontal = 4.dp).height(5.dp)
                        .width(if (index == pager.currentPage) 28.dp else 7.dp)
                        .background(if (index == pager.currentPage) EntryAccent else Color(0xFF3B424A), CircleShape)) }
                }
                Button(onClick = {
                    if (pager.currentPage == pages.lastIndex) onComplete()
                    else scope.launch { pager.animateScrollToPage(pager.currentPage + 1) }
                }, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)) {
                    Text(if (pager.currentPage == 2) "Перейти ко входу" else "Дальше", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.width(10.dp)); Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(20.dp))
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    if (pager.currentPage > 0) TextButton(onClick = { scope.launch { pager.animateScrollToPage(pager.currentPage - 1) } }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(15.dp)); Text(" Назад", fontSize = 12.sp)
                    }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = onSos) { Text("Нужна срочная помощь?", color = EntryMuted, fontSize = 11.sp) }
                }
            }
        }
    }
}

@Composable
private fun WelcomeIllustration(page: Int, icon: ImageVector, tint: Color) {
    Box(Modifier.fillMaxWidth().padding(top = 16.dp).heightIn(max = 285.dp)
        .aspectRatio(1.22f).clip(RoundedCornerShape(32.dp))
        .background(Brush.linearGradient(listOf(tint.copy(alpha = .13f), EntrySurface)))) {
        Box(Modifier.size(180.dp).align(Alignment.TopEnd).offset(x = 50.dp, y = (-30).dp)
            .background(tint.copy(alpha = .06f), CircleShape))
        Column(Modifier.align(Alignment.Center).widthIn(max = 245.dp).padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(52.dp).background(tint.copy(alpha = .15f), RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = tint, modifier = Modifier.size(30.dp))
                }
                Column(Modifier.padding(start = 12.dp)) {
                    Text(when (page) { 0 -> "Справочник"; 1 -> "Карта сервисов"; else -> "Памятки SOS" },
                        color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Text("Всегда под рукой", color = EntryMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
            when (page) {
                0 -> {
                    IllustrationRow(Icons.Default.Search, "Что вас беспокоит?", tint)
                    IllustrationRow(Icons.Default.Speed, "Разобраться в сигналах", tint)
                }
                1 -> {
                    IllustrationRow(Icons.Default.LocationOn, "Выбрать место", tint)
                    IllustrationRow(Icons.Default.NearMe, "Проложить маршрут", tint)
                }
                else -> {
                    IllustrationRow(Icons.Default.WarningAmber, "Сначала безопасность", tint)
                    IllustrationRow(Icons.Default.FormatListNumbered, "Действия по порядку", tint)
                }
            }
        }
    }
}

@Composable
private fun IllustrationRow(icon: ImageVector, text: String, tint: Color) {
    Row(Modifier.fillMaxWidth().background(Color(0xFF101214).copy(alpha = .45f), RoundedCornerShape(14.dp)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(20.dp))
        Text(text, color = Color(0xFFD6DADF), fontSize = 12.sp, modifier = Modifier.padding(start = 10.dp))
    }
}
