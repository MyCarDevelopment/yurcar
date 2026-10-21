package com.example.mycar.ui.home

import android.content.Context
import android.os.SystemClock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mycar.R
import com.example.mycar.ui.CardType
import com.example.mycar.ui.Story
import kotlinx.coroutines.delay

private object DashboardStyle {
    val background = Color(0xFF101214)
    val surface = Color(0xFF1B1E22)
    val border = Color(0xFF2B2F34)
    val muted = Color(0xFFA2A8B0)
    val accent = Color(0xFFFFA666)
}

// One list keeps thumbnails and viewer content in the same order.
private val homeStories = listOf(
    Story("Автоновости", R.drawable.news, R.drawable.st2),
    Story("В фокусе", R.drawable.newsf, R.drawable.st3),
    Story("Автомир", R.drawable.story1, R.drawable.st4),
    Story("Полезное", R.drawable.newss, R.drawable.st5),
    Story("Для водителя", R.drawable.ind38, R.drawable.st1)
)

@Composable
fun HomeScreenContent(
    backgrounds: Map<CardType, Int>,
    onCardClick: (CardType) -> Unit,
    onTelegramClick: () -> Unit,
    onOpenLegalDocuments: () -> Unit
) {
    val prefs = LocalContext.current.getSharedPreferences("home_stories", Context.MODE_PRIVATE)
    var viewed by remember { mutableStateOf(prefs.getStringSet("viewed", emptySet()).orEmpty().toSet()) }
    var selectedStory by rememberSaveable { mutableStateOf(-1) }

    MaterialTheme(colors = darkColors(
        primary = DashboardStyle.accent,
        secondary = DashboardStyle.accent,
        background = DashboardStyle.background,
        surface = DashboardStyle.surface,
        onPrimary = DashboardStyle.background,
        onSecondary = DashboardStyle.background
    )) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(DashboardStyle.background),
            contentPadding = PaddingValues(top = 14.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("ЮРКАР", color = DashboardStyle.accent, fontSize = 17.sp,
                            fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                        Text(" / ", color = DashboardStyle.border, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 8.dp))
                        Text("Всё для дороги", color = DashboardStyle.muted, fontSize = 12.sp)
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeading("В курсе событий", "СТОРИС")
                    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(homeStories.size, key = { homeStories[it].contentImageRes }) { index ->
                            StoryCover(homeStories[index], homeStories[index].contentImageRes.toString() in viewed) {
                                selectedStory = index
                            }
                        }
                    }
                }
            }
            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    GuideCard(backgrounds[CardType.BUY_CAR] ?: R.drawable.test) { onCardClick(CardType.BUY_CAR) }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FeatureCard("Услуги", "Сервисы и специалисты", Icons.Default.Build,
                            Color(0xFF9BD5C0), Modifier.weight(1f)) { onCardClick(CardType.SERVICES) }
                        FeatureCard("Товары", "Всё для автомобиля", Icons.Default.ShoppingBag,
                            Color(0xFFFFD88D), Modifier.weight(1f)) { onCardClick(CardType.SIXTH) }
                    }
                    Surface(Modifier.padding(horizontal = 20.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)).clickable { onCardClick(CardType.SELL_CAR) },
                        color = DashboardStyle.surface, shape = RoundedCornerShape(20.dp)) {
                        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                            IconTile(Icons.Default.ReceiptLong, DashboardStyle.accent)
                            Column(Modifier.weight(1f).padding(horizontal = 14.dp)) {
                                Text("Проверка и оплата штрафов", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                Text("Перейти к проверке", color = DashboardStyle.muted, fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp))
                            }
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = DashboardStyle.muted)
                        }
                    }
                }
            }
            item { TelegramCard(onTelegramClick) }
            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onOpenLegalDocuments).padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, null, tint = DashboardStyle.muted, modifier = Modifier.size(20.dp))
                        Text("Документы и конфиденциальность", color = DashboardStyle.muted, fontSize = 13.sp,
                            modifier = Modifier.weight(1f).padding(horizontal = 10.dp))
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = DashboardStyle.muted)
                    }
                    Divider(color = DashboardStyle.border)
                    Text("Юркар · Спокойнее за рулём", color = DashboardStyle.muted.copy(alpha = .65f),
                        fontSize = 11.sp, modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
        if (selectedStory in homeStories.indices) {
            StoryViewer(homeStories, selectedStory, onDismiss = { selectedStory = -1 }, onViewed = { story ->
                viewed = viewed + story.contentImageRes.toString()
                prefs.edit().putStringSet("viewed", viewed).apply()
            })
        }
    }
}

@Composable
private fun SectionHeading(title: String, eyebrow: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(eyebrow, color = DashboardStyle.muted, fontSize = 9.sp, letterSpacing = 1.2.sp)
    }
}

@Composable
private fun StoryCover(story: Story, viewed: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(20.dp)
    val index = homeStories.indexOf(story).coerceAtLeast(0)
    val icons = listOf(Icons.Default.Newspaper, Icons.Default.AutoStories, Icons.Default.DirectionsCar,
        Icons.Default.Lightbulb, Icons.Default.AcUnit)
    Column(Modifier.width(96.dp).clip(shape).clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(width = 96.dp, height = 70.dp)
            .border(1.5.dp, if (viewed) DashboardStyle.border else DashboardStyle.accent, shape)
            .padding(4.dp).clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF30383E), Color(0xFF1B2025)))), contentAlignment = Alignment.Center) {
            Box(Modifier.offset(x = 31.dp, y = 24.dp).size(66.dp).border(1.dp, Color.White.copy(alpha = .07f), CircleShape))
            Icon(icons[index % icons.size], null, tint = Color(0xFFD4C4B8), modifier = Modifier.size(28.dp))
            if (!viewed) Box(Modifier.align(Alignment.TopEnd).padding(8.dp).size(5.dp).background(DashboardStyle.accent, CircleShape))
        }
        Text(story.title, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium,
            maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 7.dp, bottom = 2.dp))
    }
}

@Composable
private fun GuideCard(image: Int, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))
        .background(Color(0xFF283342)).clickable(onClick = onClick)) {
        Image(painterResource(image), null, contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(), alignment = Alignment.CenterEnd)
        Box(Modifier.matchParentSize().background(Brush.horizontalGradient(listOf(
            Color(0xFF151E29), Color(0xFF151E29).copy(alpha = .94f), Color(0xFF151E29).copy(alpha = .22f)))))
        Column(Modifier.widthIn(max = 255.dp).padding(18.dp)) {
            Text("ВАШ АВТОПОМОЩНИК", color = DashboardStyle.accent, fontSize = 9.sp,
                letterSpacing = 1.6.sp, fontWeight = FontWeight.Bold)
            Text("Справочник\nводителя", color = Color.White, fontSize = 23.sp, lineHeight = 26.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text("От симптома — к понятным действиям", color = Color(0xFFB7C2CE), fontSize = 11.sp,
                lineHeight = 16.sp, modifier = Modifier.padding(top = 6.dp))
            Row(Modifier.padding(top = 12.dp).background(DashboardStyle.accent, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Открыть справочник", color = DashboardStyle.background, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = DashboardStyle.background, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun FeatureCard(title: String, subtitle: String, icon: ImageVector, accent: Color,
                        modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(modifier.clip(RoundedCornerShape(20.dp)).clickable(onClick = onClick),
        color = DashboardStyle.surface, shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, DashboardStyle.border.copy(alpha = .6f))) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top) {
                IconTile(icon, accent)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = DashboardStyle.muted,
                    modifier = Modifier.size(18.dp))
            }
            Text(title, color = Color.White, fontSize = 17.sp, lineHeight = 22.sp,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 12.dp))
            Text(subtitle, color = DashboardStyle.muted, fontSize = 11.sp, lineHeight = 16.sp,
                modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
private fun IconTile(icon: ImageVector, accent: Color) {
    Box(Modifier.size(42.dp).background(accent.copy(alpha = .10f), RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center) {
        Icon(icon, null, tint = accent, modifier = Modifier.size(23.dp))
    }
}

@Composable
private fun TelegramCard(onClick: () -> Unit) {
    Column(Modifier.padding(horizontal = 20.dp).fillMaxWidth().clip(RoundedCornerShape(24.dp))
        .background(Brush.linearGradient(listOf(Color(0xFF233C4C), Color(0xFF192831))))
        .clickable(onClick = onClick).padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("СВОИ ЛЮДИ", color = Color(0xFF9CCEE9), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.8.sp)
            Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color(0xFF8DCEEF), modifier = Modifier.size(28.dp))
        }
        Text("На одной дороге", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp))
        Text("Опыт и поддержка водителей в Telegram", color = Color(0xFFB2C5D1),
            fontSize = 12.sp, lineHeight = 17.sp, modifier = Modifier.padding(top = 4.dp))
        Row(Modifier.padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Вступить в сообщество", color = Color(0xFF9DD7F7), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color(0xFF9DD7F7), modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun StoryViewer(stories: List<Story>, initialIndex: Int, onDismiss: () -> Unit,
                onViewed: (Story) -> Unit = {}) {
    if (stories.isEmpty()) return
    var page by rememberSaveable { mutableStateOf(initialIndex.coerceIn(stories.indices)) }
    var progress by remember(page) { mutableStateOf(0f) }
    var held by remember { mutableStateOf(false) }
    var dragging by remember { mutableStateOf(false) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var active by remember { mutableStateOf(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) }
    val currentViewed by rememberUpdatedState(onViewed)
    val dismiss by rememberUpdatedState(onDismiss)
    val dragThreshold = with(LocalDensity.current) { 100.dp.toPx() }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, _ ->
            active = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
            if (!active) { held = false; dragging = false }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
    // Opening a story briefly is not enough to mark it as read.
    LaunchedEffect(page, active) {
        if (active) { delay(600); currentViewed(stories[page]) }
    }
    LaunchedEffect(page, held, dragging, active) {
        if (held || dragging || !active) return@LaunchedEffect
        var previous = SystemClock.elapsedRealtime()
        while (progress < 1f) {
            delay(30)
            val now = SystemClock.elapsedRealtime()
            progress = (progress + (now - previous) / 12000f).coerceAtMost(1f)
            previous = now
        }
        if (page < stories.lastIndex) page++ else dismiss()
    }
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false)) {
        Column(Modifier.fillMaxSize().background(Color(0xFF0B0D10)).systemBarsPadding()) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                stories.indices.forEach { index ->
                    LinearProgressIndicator(progress = when { index < page -> 1f; index == page -> progress; else -> 0f },
                        color = DashboardStyle.accent, backgroundColor = Color.White.copy(alpha = .2f),
                        modifier = Modifier.weight(1f).height(3.dp).clip(CircleShape))
                }
            }
            Row(Modifier.fillMaxWidth().padding(start = 20.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(stories[page].title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text("${page + 1} из ${stories.size}", color = DashboardStyle.muted, fontSize = 11.sp)
                }
                IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, "Закрыть сторис", tint = Color.White) }
            }
            Box(Modifier.weight(1f).fillMaxWidth()
                .pointerInput(page) {
                    detectTapGestures(onPress = {
                        held = true
                        try { tryAwaitRelease() } finally { held = false }
                    }, onLongPress = { /* Reading pause ends on release without advancing. */ }, onTap = { position ->
                        if (position.x < size.width / 3f) { if (page > 0) page-- else progress = 0f }
                        else if (page < stories.lastIndex) page++ else dismiss()
                    })
                }
                .pointerInput(Unit) {
                    var distance = 0f
                    var horizontal = 0f
                    detectDragGestures(onDragStart = { dragging = true; distance = 0f; horizontal = 0f },
                        onDragCancel = { dragging = false }, onDragEnd = {
                            dragging = false
                            if (distance > dragThreshold && distance > kotlin.math.abs(horizontal)) dismiss()
                            else if (kotlin.math.abs(horizontal) > dragThreshold) {
                                if (horizontal < 0) { if (page < stories.lastIndex) page++ else dismiss() }
                                else if (page > 0) page-- else progress = 0f
                            }
                        }, onDrag = { change, amount ->
                            change.consume(); distance += amount.y; horizontal += amount.x
                        })
                }) {
                Image(painterResource(stories[page].contentImageRes), stories[page].title,
                    contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (page > 0) page-- else progress = 0f }) {
                    Icon(Icons.Default.ChevronLeft, "Предыдущая сторис", tint = Color.White)
                }
                Text("Удерживайте, чтобы читать", color = DashboardStyle.muted, fontSize = 12.sp,
                    modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                IconButton(onClick = { if (page < stories.lastIndex) page++ else dismiss() }) {
                    Icon(Icons.Default.ChevronRight, "Следующая сторис", tint = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(mapOf(CardType.BUY_CAR to R.drawable.test), {}, {}, {})
}
