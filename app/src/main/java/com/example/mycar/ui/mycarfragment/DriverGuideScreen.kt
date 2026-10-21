package com.example.mycar.ui.mycarfragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GuideBg = Color(0xFF101214)
private val GuideSurface = Color(0xFF1B1E22)
private val GuideBorder = Color(0xFF2B2F34)
private val GuideMuted = Color(0xFFA2A8B0)
private val GuideAccent = Color(0xFFFFA666)

@Composable
private fun GuideTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = darkColors(primary = GuideAccent, secondary = GuideAccent,
        background = GuideBg, surface = GuideSurface, onPrimary = GuideBg, onSecondary = GuideBg), content = content)
}

@Composable
fun GuideHubScreen(onBack: () -> Unit, onSymptoms: () -> Unit, onLocation: () -> Unit,
                   onIndicators: () -> Unit, onUrgency: () -> Unit, onRoadHelp: () -> Unit,
                   onMaintenance: () -> Unit, onTips: () -> Unit = {}) {
    GuideTheme {
        Column(Modifier.fillMaxSize().background(GuideBg)) {
            GuideTopBar("Справочник водителя", onBack)
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                item {
                    Text("Разберёмся по порядку", fontSize = 26.sp, lineHeight = 31.sp,
                        fontWeight = FontWeight.Bold, color = Color.White)
                    Text("От первого признака до понятного следующего шага.", color = GuideMuted,
                        fontSize = 14.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                }
                item { GuideNotice("Сначала безопасность", "Если изменились тормоза или управление, появился дым, сильный запах топлива или красное предупреждение — остановитесь в безопасном месте.", Color(0xFFFFB3A5)) }
                item { Text("НАЙТИ ПРИЧИНУ", color = GuideMuted, letterSpacing = 1.6.sp, fontSize = 10.sp, modifier = Modifier.padding(top = 8.dp)) }
                item { HubRow("По симптомам", "Шум, вибрация, запах, запуск", Icons.Default.Troubleshoot, GuideAccent, onSymptoms) }
                item { HubRow("По местоположению", "Под капотом, у колёс или в салоне", Icons.Default.LocationOn, Color(0xFF9BD5C0), onLocation) }
                item { HubRow("Приборная панель", "Значки и сообщения автомобиля", Icons.Default.Speed, Color(0xFFB7C6FF), onIndicators) }
                item { HubRow("Насколько срочно?", "Когда остановиться и когда записаться в сервис", Icons.Default.PriorityHigh, Color(0xFFFFB3A5), onUrgency) }
                item { Text("ПОМОЩЬ И УХОД", color = GuideMuted, letterSpacing = 1.6.sp, fontSize = 10.sp, modifier = Modifier.padding(top = 8.dp)) }
                item { HubRow("Помощь на дороге", "Поломка, прокол, аккумулятор, топливо", Icons.Default.SupportAgent, Color(0xFF9BCFEA), onRoadHelp) }
                item { HubRow("Обслуживание и расходники", "Масло, фильтры, шины, жидкости и приводы", Icons.Default.Build, Color(0xFFFFD88D), onMaintenance) }
                item { HubRow("Полезные советы", "Наглядные карточки: проверяем и ухаживаем", Icons.Default.Lightbulb, Color(0xFFB7C6FF), onTips) }
                item { Text("Подсказки помогают сориентироваться. Точный диагноз и регламент зависят от модели автомобиля.", color = GuideMuted,
                    fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(vertical = 8.dp)) }
            }
        }
    }
}

@Composable
private fun GuideTopBar(title: String, onBack: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(end = 16.dp, top = 4.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = GuideAccent) }
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
private fun HubRow(title: String, subtitle: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Surface(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).clickable(onClick = onClick),
        color = GuideSurface, shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, GuideBorder)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).background(color.copy(alpha = .10f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            }
            Column(Modifier.weight(1f).padding(horizontal = 14.dp)) {
                Text(title, fontSize = 16.sp, lineHeight = 21.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(subtitle, fontSize = 12.sp, lineHeight = 18.sp, color = GuideMuted, modifier = Modifier.padding(top = 5.dp))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = GuideMuted, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun DriverGuideSection(section: GuideSection, onBack: () -> Unit, initialArticleId: String? = null) {
    var query by rememberSaveable(section) { mutableStateOf("") }
    var category by rememberSaveable(section) { mutableStateOf("Все") }
    var selectedId by rememberSaveable(section) { mutableStateOf(initialArticleId) }
    val all = remember(section) { articlesFor(section) }
    val selected = all.firstOrNull { it.id == selectedId }
    val listState = rememberLazyListState()
    val categories = remember(section) {
        listOf("Все") + when (section) {
            GuideSection.LOCATION -> all.map { it.area }.distinct()
            GuideSection.URGENCY -> GuideUrgency.entries.map { it.label }.filter { label -> all.any { it.urgency.label == label } }
            else -> all.map { it.category }.distinct()
        }
    }
    val filtered = remember(section, query, category) { findGuideArticles(section, query, category) }
    BackHandler(enabled = selected != null) { selectedId = null }
    GuideTheme {
        Column(Modifier.fillMaxSize().background(GuideBg)) {
            GuideTopBar(if (selected != null) "К списку · ${section.title}" else section.title) {
                if (selected != null) selectedId = null else onBack()
            }
            if (selected != null) {
                key(selected.id) { ArticleDetail(selected) }
            } else {
                LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        Column(Modifier.padding(horizontal = 20.dp)) {
                            Text(section.title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                            Text(section.subtitle, color = GuideMuted, fontSize = 14.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 8.dp))
                            OutlinedTextField(value = query, onValueChange = { query = it },
                                modifier = Modifier.fillMaxWidth().padding(top = 18.dp), singleLine = true,
                                shape = RoundedCornerShape(16.dp), placeholder = { Text("Что вас беспокоит?", fontSize = 14.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, null, tint = GuideMuted) },
                                trailingIcon = { if (query.isNotEmpty()) IconButton(onClick = { query = "" }) { Icon(Icons.Default.Close, "Очистить поиск") } },
                                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White,
                                    backgroundColor = GuideSurface, unfocusedBorderColor = GuideBorder, focusedBorderColor = GuideAccent))
                        }
                    }
                    item {
                        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(categories) { facet ->
                                Surface(Modifier.clip(RoundedCornerShape(12.dp)).clickable { category = facet },
                                    color = if (category == facet) GuideAccent else GuideSurface, shape = RoundedCornerShape(12.dp)) {
                                    Text(facet, color = if (category == facet) GuideBg else GuideMuted,
                                        fontSize = 12.sp, modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp))
                                }
                            }
                        }
                    }
                    if (section == GuideSection.URGENCY) item {
                        Box(Modifier.padding(horizontal = 20.dp)) { GuideNotice("Оценивайте весь набор признаков", "Категория — ориентир, а не разрешение продолжать движение. При ухудшении тормозов, управления, появлении дыма или сильной вибрации остановитесь безопасно.", Color(0xFFFFB3A5)) }
                    }
                    if (section == GuideSection.MAINTENANCE) item {
                        Box(Modifier.padding(horizontal = 20.dp)) { GuideNotice("Регламент — для вашей модели", "Универсального пробега замены нет. Сверяйте срок, допуск и условия эксплуатации с сервисной книжкой.", GuideAccent) }
                    }
                    item { Text("МАТЕРИАЛОВ: ${filtered.size}", color = GuideMuted, fontSize = 10.sp,
                        letterSpacing = 1.4.sp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) }
                    if (filtered.isEmpty()) item {
                        Column(Modifier.padding(24.dp)) {
                            Text("Ничего не нашлось", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Text("Попробуйте короткое описание или другую категорию.", color = GuideMuted, modifier = Modifier.padding(top = 8.dp))
                            TextButton(onClick = { query = ""; category = "Все" }) { Text("Сбросить поиск") }
                        }
                    }
                    items(filtered, key = { it.id }) { article ->
                        Surface(Modifier.padding(horizontal = 20.dp).fillMaxWidth().clip(RoundedCornerShape(20.dp))
                            .clickable { selectedId = article.id }, color = GuideSurface,
                            shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, GuideBorder)) {
                            Column(Modifier.padding(18.dp)) {
                                UrgencyBadge(article.urgency)
                                Text(article.title, fontSize = 18.sp, lineHeight = 24.sp, color = Color.White,
                                    fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 12.dp))
                                Text(article.summary, color = GuideMuted, fontSize = 13.sp, lineHeight = 20.sp,
                                    modifier = Modifier.padding(top = 6.dp))
                                Row(Modifier.fillMaxWidth().padding(top = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(if (section == GuideSection.LOCATION) article.area else article.category,
                                        color = GuideMuted, fontSize = 11.sp, modifier = Modifier.weight(1f))
                                    Text("Что делать", color = GuideAccent, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = GuideAccent, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun GuideUrgency.color(): Color = when (this) {
    GuideUrgency.STOP -> Color(0xFFFFB3A5)
    GuideUrgency.SOON -> GuideAccent
    GuideUrgency.ROUTINE -> Color(0xFF9BD5C0)
}

@Composable
private fun UrgencyBadge(urgency: GuideUrgency) {
    Text(urgency.label, color = urgency.color(), fontSize = 10.sp, lineHeight = 15.sp,
        fontWeight = FontWeight.Medium, modifier = Modifier.background(urgency.color().copy(alpha = .1f),
            RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 6.dp))
}

@Composable
private fun GuideNotice(title: String, text: String, accent: Color) {
    Column(Modifier.fillMaxWidth().background(accent.copy(alpha = .07f), RoundedCornerShape(18.dp)).padding(16.dp)) {
        Text(title, color = accent, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Text(text, color = GuideMuted, fontSize = 12.sp, lineHeight = 19.sp, modifier = Modifier.padding(top = 6.dp))
    }
}

@Composable
internal fun ArticleDetail(article: GuideArticle) {
    val context = LocalContext.current
    LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        item {
            UrgencyBadge(article.urgency)
            Text(article.title, color = Color.White, fontSize = 28.sp, lineHeight = 34.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp))
            Text(article.summary, color = GuideMuted, fontSize = 15.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 10.dp))
        }
        item {
            Surface(color = GuideSurface, shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(if (article.section == GuideSection.MAINTENANCE) "Как проверить и спланировать" else "Что сделать сейчас",
                        color = GuideAccent, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    article.actions.forEachIndexed { index, action ->
                        Row {
                            Text("0${index + 1}", color = GuideAccent, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(30.dp))
                            Text(action, color = Color.White, fontSize = 14.sp, lineHeight = 23.sp)
                        }
                    }
                }
            }
        }
        item {
            Text(if (article.section == GuideSection.SYMPTOMS) "С чем это может быть связано" else "Что важно учитывать",
                color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            article.explanations.forEach { text ->
                Text("•  $text", color = GuideMuted, fontSize = 14.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 10.dp))
            }
        }
        item { GuideNotice("Чего не стоит делать", article.avoid, Color(0xFFFFB3A5)) }
        item { GuideNotice("Что сообщить мастеру", article.serviceNote, Color(0xFF9BCFEA)) }
        item {
            Text("Это ориентир, а не диагноз. Сообщения автомобиля и руководство именно вашей модели имеют приоритет.",
                color = GuideMuted, fontSize = 12.sp, lineHeight = 19.sp)
            TextButton(onClick = {
                try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.source))) }
                catch (_: ActivityNotFoundException) { Toast.makeText(context, "Не найден браузер для открытия материала", Toast.LENGTH_SHORT).show() }
            }, contentPadding = PaddingValues(vertical = 12.dp)) {
                Icon(Icons.Default.OpenInNew, null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Справочный материал · ${Uri.parse(article.source).host}", fontSize = 11.sp)
            }
        }
    }
}
