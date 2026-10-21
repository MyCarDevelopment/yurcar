package com.example.mycar.ui.mycarfragment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mycar.BaseActivity
import kotlinx.coroutines.launch

class UsefulTipsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent { UsefulTipsScreen(onBack = { finish() }) }
    }
}

private data class VisualTip(val articleId: String, val title: String, val icon: ImageVector, val color: Color)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsefulTipsScreen(onBack: () -> Unit) {
    val tips = remember { listOf(
        VisualTip("brake-service", "Тормоза без сюрпризов", Icons.Default.CarRepair, Color(0xFFFFB3A5)),
        VisualTip("engine-oil", "Масло: важен допуск", Icons.Default.OilBarrel, Color(0xFFFFD88D)),
        VisualTip("tyres", "Шины перед поездкой", Icons.Default.TireRepair, Color(0xFF9BD5C0)),
        VisualTip("engine-light", "Не стирайте ошибки", Icons.Default.Troubleshoot, Color(0xFFB7C6FF)),
        VisualTip("flat-battery", "Аккумулятор и запуск", Icons.Default.BatteryChargingFull, Color(0xFF9BCFEA)),
        VisualTip("demist", "Хороший обзор", Icons.Default.Air, Color(0xFFD6C6EF))
    ) }
    val pager = rememberPagerState { tips.size }
    val scope = rememberCoroutineScope()
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val accent = Color(0xFFFFA666)
    MaterialTheme(colors = darkColors(primary = accent, onPrimary = Color(0xFF101214),
        background = Color(0xFF101214), surface = Color(0xFF1B1E22))) {
        Column(Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = accent) }
                Text("Полезные советы", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.weight(1f))
                Text("${pager.currentPage + 1} / ${tips.size}", color = accent, fontSize = 12.sp, modifier = Modifier.padding(end = 20.dp))
            }
            Text("Маленькие привычки.\nБольше спокойствия.", color = Color.White, fontSize = 26.sp,
                fontWeight = FontWeight.Bold, lineHeight = 31.sp, modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp))
            HorizontalPager(state = pager, contentPadding = PaddingValues(horizontal = 22.dp), pageSpacing = 14.dp,
                modifier = Modifier.weight(1f).fillMaxWidth()) { page ->
                val tip = tips[page]
                val article = guideArticles.first { it.id == tip.articleId }
                Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(26.dp), modifier = Modifier.fillMaxSize()) {
                    androidx.compose.foundation.lazy.LazyColumn(contentPadding = PaddingValues(22.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)) {
                        item {
                            Box(Modifier.fillMaxWidth().height(140.dp).background(Brush.linearGradient(
                                listOf(tip.color.copy(alpha = .15f), Color(0xFF20252B))), RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
                                Box(Modifier.size(108.dp).background(tip.color.copy(alpha = .05f), CircleShape))
                                Icon(tip.icon, null, tint = tip.color, modifier = Modifier.size(60.dp))
                            }
                        }
                        item {
                            Text(article.category.uppercase(), color = tip.color, fontSize = 10.sp, letterSpacing = 1.8.sp)
                            Text(tip.title, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold,
                                lineHeight = 30.sp, modifier = Modifier.padding(top = 10.dp))
                            Text(article.summary, color = Color(0xFFA2A8B0), fontSize = 14.sp, lineHeight = 22.sp,
                                modifier = Modifier.padding(top = 10.dp))
                        }
                        item {
                            Text("Возьмите за правило", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            article.actions.take(2).forEach { action ->
                                Text("•  $action", color = Color(0xFFBDC2C8), fontSize = 13.sp, lineHeight = 21.sp,
                                    modifier = Modifier.padding(top = 12.dp))
                            }
                        }
                        item { Button(onClick = { selectedId = article.id }, shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Разобраться подробнее") } }
                    }
                }
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(enabled = pager.currentPage > 0, onClick = { scope.launch { pager.animateScrollToPage(pager.currentPage - 1) } }) {
                    Icon(Icons.Default.ChevronLeft, "Предыдущий совет", tint = if (pager.currentPage > 0) Color.White else Color(0xFF545B63))
                }
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    tips.indices.forEach { index ->
                        Box(Modifier.padding(3.dp).size(if (index == pager.currentPage) 8.dp else 6.dp)
                            .background(if (index == pager.currentPage) accent else Color(0xFF41474F), CircleShape))
                    }
                }
                IconButton(enabled = pager.currentPage < tips.lastIndex, onClick = { scope.launch { pager.animateScrollToPage(pager.currentPage + 1) } }) {
                    Icon(Icons.Default.ChevronRight, "Следующий совет", tint = if (pager.currentPage < tips.lastIndex) Color.White else Color(0xFF545B63))
                }
            }
        }
        guideArticles.firstOrNull { it.id == selectedId }?.let { article ->
            Dialog(onDismissRequest = { selectedId = null }, properties = DialogProperties(usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false)) {
                Column(Modifier.fillMaxSize().background(Color(0xFF101214)).systemBarsPadding()) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { selectedId = null }) { Icon(Icons.Default.Close, "Закрыть совет", tint = accent) }
                        Text("Полезные советы", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                    Box(Modifier.weight(1f)) { ArticleDetail(article) }
                }
            }
        }
    }
}
