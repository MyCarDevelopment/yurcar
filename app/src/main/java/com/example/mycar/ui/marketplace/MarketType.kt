package com.example.mycar.ui.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class ProductConcept(val title: String, val description: String, val category: String, val icon: ImageVector)

@Composable
fun ProductsScreen(onBack: () -> Unit) {
    val accent = Color(0xFFFFA666)
    val muted = Color(0xFFA2A8B0)
    val categories = listOf("Все", "Уход", "В дорогу", "Аксессуары")
    var selected by rememberSaveable { mutableStateOf("Все") }
    val concepts = remember { listOf(
        ProductConcept("Масла и жидкости", "Подбор по допускам автомобиля", "Уход", Icons.Default.OilBarrel),
        ProductConcept("Шины и колёса", "Размеры и сезонность", "В дорогу", Icons.Default.TireRepair),
        ProductConcept("Уход за автомобилем", "Чистый кузов и уютный салон", "Уход", Icons.Default.AutoAwesome),
        ProductConcept("Для дальней поездки", "Компрессор и полезные мелочи", "В дорогу", Icons.Default.Luggage),
        ProductConcept("Аксессуары в салон", "Организация и комфорт", "Аксессуары", Icons.Default.DirectionsCar),
        ProductConcept("Зарядки и держатели", "Всё под рукой в пути", "Аксессуары", Icons.Default.Power)
    ) }
    val filtered = concepts.filter { selected == "Все" || it.category == selected }
    MaterialTheme(colors = darkColors(primary = accent, onPrimary = Color(0xFF101214),
        background = Color(0xFF101214), surface = Color(0xFF1B1E22))) {
        Column(Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = accent) }
                Text("Товары", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                item {
                    Column(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Color(0xFF403522), Color(0xFF242524))),
                        RoundedCornerShape(24.dp)).padding(22.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ПАРТНЁРСКАЯ ВИТРИНА", color = Color(0xFFFFD88D), fontSize = 10.sp, letterSpacing = 1.5.sp)
                            Icon(Icons.Default.ShoppingBag, null, tint = Color(0xFFFFD88D))
                        }
                        Text("Полезные покупки\nдля вашего авто", color = Color.White, fontSize = 27.sp,
                            lineHeight = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 18.dp))
                        Text("Будущие подборки предложений через Яндекс Дистрибуцию.", color = Color(0xFFC9C1B3),
                            fontSize = 13.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 12.dp))
                        Text("Витрина скоро откроется", color = Color(0xFFFFD88D), fontSize = 12.sp,
                            modifier = Modifier.padding(top = 18.dp).background(Color.White.copy(alpha = .06f),
                                RoundedCornerShape(10.dp)).padding(horizontal = 12.dp, vertical = 8.dp))
                    }
                }
                item {
                    Text("Подборки по задачам", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                    Text("Пока это пример будущей витрины — без цен, заказов и переходов к продавцам.", color = muted,
                        fontSize = 12.sp, lineHeight = 19.sp, modifier = Modifier.padding(top = 8.dp))
                }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categories) { category ->
                            Surface(Modifier.clip(RoundedCornerShape(12.dp)).clickable { selected = category },
                                color = if (selected == category) accent else MaterialTheme.colors.surface,
                                shape = RoundedCornerShape(12.dp)) {
                                Text(category, color = if (selected == category) Color(0xFF101214) else muted,
                                    fontSize = 12.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                            }
                        }
                    }
                }
                items(filtered.chunked(2)) { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.height(IntrinsicSize.Min)) {
                        row.forEach { concept ->
                            Surface(Modifier.weight(1f).fillMaxHeight(), color = MaterialTheme.colors.surface, shape = RoundedCornerShape(20.dp)) {
                                Column(Modifier.padding(16.dp)) {
                                    Box(Modifier.fillMaxWidth().height(86.dp).background(Color(0xFF292D31), RoundedCornerShape(15.dp)),
                                        contentAlignment = Alignment.Center) {
                                        Icon(concept.icon, null, tint = Color(0xFFDDC9B5), modifier = Modifier.size(36.dp))
                                    }
                                    Text(concept.title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
                                        lineHeight = 21.sp, modifier = Modifier.padding(top = 14.dp))
                                    Text(concept.description, color = muted, fontSize = 12.sp, lineHeight = 18.sp,
                                        modifier = Modifier.padding(top = 7.dp))
                                    Spacer(Modifier.weight(1f))
                                    Text("Скоро в подборке", color = accent, fontSize = 10.sp, modifier = Modifier.padding(top = 16.dp))
                                }
                            }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                item {
                    Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(18.dp)) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Как это будет работать", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("Выберите предложение в Юркаре → перейдите на сайт партнёра → проверьте совместимость, стоимость и условия покупки там.",
                                color = muted, fontSize = 13.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 10.dp))
                            Text("Советы по выбору и уходу уже доступны в справочнике водителя.", color = accent,
                                fontSize = 12.sp, lineHeight = 19.sp, modifier = Modifier.padding(top = 12.dp))
                        }
                    }
                }
            }
        }
    }
}
