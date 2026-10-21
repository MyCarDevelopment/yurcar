package com.example.mycar.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MapUiState(
    val query: String = "",
    val types: Set<PointType> = emptySet(),
    val matches: List<MapPlace> = mapPlaces,
    val selected: MapPlace? = null,
    val locating: Boolean = false,
    val online: Boolean = true,
    val message: String? = null
)

private val MapBg = Color(0xFF1B1E22)
private val MapMuted = Color(0xFFADB4BC)
private val MapAccent = Color(0xFFFFA666)

@Composable
fun ServiceMapScreen(state: MapUiState, onQuery: (String) -> Unit, onTypes: (Set<PointType>) -> Unit,
                     onSelect: (MapPlace?) -> Unit, onLocate: () -> Unit, onZoom: (Float) -> Unit,
                     onRoute: (MapPlace) -> Unit, onDismissMessage: () -> Unit,
                     bottomInset: androidx.compose.ui.unit.Dp = 16.dp, mapContent: @Composable () -> Unit) {
    var showList by rememberSaveable { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(state.selected) { if (state.selected != null) { showList = false; keyboard?.hide() } }
    MaterialTheme(colors = darkColors(primary = MapAccent, onPrimary = Color(0xFF101214), surface = MapBg)) {
        BoxWithConstraints(Modifier.fillMaxSize().background(Color(0xFF171B1F))) {
            val compact = maxHeight < 420.dp
            val panelMaxHeight = maxHeight * if (compact) .48f else .43f
            mapContent()
            Surface(Modifier.align(Alignment.TopCenter).padding(14.dp).fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                color = MapBg.copy(alpha = .97f), elevation = 6.dp) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (!compact) Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Сервисы на карте", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                            Text("Санкт-Петербург · подборка мест", color = MapMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 3.dp))
                        }
                        TextButton(onClick = { showList = !showList; onSelect(null); keyboard?.hide() }) {
                            Icon(Icons.AutoMirrored.Filled.List, null, modifier = Modifier.size(18.dp))
                            Text(" ${state.matches.size}", fontSize = 13.sp)
                        }
                    }
                    OutlinedTextField(state.query, onQuery, modifier = Modifier.fillMaxWidth(), singleLine = true,
                        placeholder = { Text("Название, услуга или адрес", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MapMuted) },
                        trailingIcon = { if (state.query.isNotEmpty()) IconButton(onClick = { onQuery("") }) { Icon(Icons.Default.Close, "Очистить поиск") } },
                        shape = RoundedCornerShape(14.dp), textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White,
                            unfocusedBorderColor = Color(0xFF3C4249), focusedBorderColor = MapAccent))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        item { MapFilter("Все", state.types.isEmpty()) { onTypes(emptySet()) } }
                        items(PointType.entries) { type ->
                            MapFilter(type.label, type in state.types) {
                                onTypes(if (type in state.types) state.types - type else state.types + type)
                            }
                        }
                    }
                    if (!state.online) Text("Нет подключения. Загруженные участки карты могут оставаться доступны.", color = MapAccent, fontSize = 11.sp)
                    if (compact) TextButton(onClick = { showList = !showList; onSelect(null) }) { Text("Список мест · ${state.matches.size}") }
                }
            }
            Column(Modifier.align(Alignment.CenterEnd).padding(end = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MapControl("Приблизить", Icons.Default.Add) { onZoom(1f) }
                MapControl("Отдалить", Icons.Default.Remove) { onZoom(-1f) }
                Surface(shape = RoundedCornerShape(16.dp), color = MapBg, elevation = 4.dp) {
                    IconButton(onClick = onLocate, enabled = !state.locating) {
                        if (state.locating) CircularProgressIndicator(Modifier.size(20.dp), color = MapAccent, strokeWidth = 2.dp)
                        else Icon(Icons.Default.MyLocation, "Моё местоположение", tint = MapAccent)
                    }
                }
            }
            Column(Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(start = 14.dp, end = 14.dp, bottom = bottomInset),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.message?.let { message ->
                    Surface(shape = RoundedCornerShape(16.dp), color = MapBg, elevation = 4.dp) {
                        Row(Modifier.padding(start = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(message, color = Color.White, fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.weight(1f).padding(vertical = 10.dp))
                            IconButton(onClick = onDismissMessage) { Icon(Icons.Default.Close, "Скрыть сообщение", tint = MapMuted) }
                        }
                    }
                }
                if (state.matches.isEmpty()) Surface(shape = RoundedCornerShape(20.dp), color = MapBg, elevation = 6.dp) {
                    Column(Modifier.fillMaxWidth().padding(18.dp)) {
                        Text("Места не найдены", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Попробуйте другой запрос или категорию.", color = MapMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp))
                        TextButton(onClick = { onQuery(""); onTypes(emptySet()) }) { Text("Сбросить фильтры") }
                    }
                }
                state.selected?.let { place ->
                    Surface(shape = RoundedCornerShape(22.dp), color = MapBg, elevation = 8.dp) {
                        Column(Modifier.fillMaxWidth().heightIn(max = panelMaxHeight).verticalScroll(rememberScrollState()).padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(place.type.label.uppercase(), color = MapAccent, fontSize = 10.sp, letterSpacing = 1.sp, modifier = Modifier.weight(1f))
                                IconButton(onClick = { onSelect(null) }, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.Close, "Закрыть карточку места", tint = MapMuted) }
                            }
                            Text(place.companyName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 24.sp)
                            Text(place.services.joinToString(" · "), color = MapMuted, fontSize = 12.sp, lineHeight = 19.sp,
                                modifier = Modifier.padding(top = 8.dp))
                            Button(onClick = { onRoute(place) }, shape = RoundedCornerShape(13.dp),
                                modifier = Modifier.fillMaxWidth().padding(top = 14.dp).heightIn(min = 48.dp)) {
                                Icon(Icons.Default.NearMe, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp)); Text("Проложить маршрут")
                            }
                            Text("Услуги и адрес уточняйте перед поездкой", color = MapMuted, fontSize = 10.sp, modifier = Modifier.padding(top = 10.dp))
                        }
                    }
                }
                if (showList && state.selected == null && state.matches.isNotEmpty()) Surface(shape = RoundedCornerShape(22.dp), color = MapBg, elevation = 8.dp) {
                    Column {
                        Row(Modifier.padding(start = 18.dp, end = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("Найдено мест: ${state.matches.size}", color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                            IconButton(onClick = { showList = false }) { Icon(Icons.Default.Close, "Закрыть список", tint = MapMuted) }
                        }
                        LazyColumn(Modifier.heightIn(max = panelMaxHeight)) {
                            items(state.matches, key = { it.companyName }) { place ->
                                Column(Modifier.fillMaxWidth().clickable { onSelect(place); showList = false }.padding(horizontal = 18.dp, vertical = 14.dp)) {
                                    Text(place.companyName, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Text(place.type.label, color = MapAccent, fontSize = 11.sp, modifier = Modifier.padding(top = 5.dp))
                                    Text(place.services.lastOrNull().orEmpty(), color = MapMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                                }
                                Divider(color = Color(0xFF30363C))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MapFilter(label: String, checked: Boolean, onClick: () -> Unit) {
    Surface(Modifier.clip(RoundedCornerShape(11.dp)).clickable(onClick = onClick), shape = RoundedCornerShape(11.dp),
        color = if (checked) MapAccent else Color(0xFF2C3238)) {
        Text(label, color = if (checked) Color(0xFF101214) else MapMuted, fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp))
    }
}

@Composable
private fun MapControl(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(shape = RoundedCornerShape(16.dp), color = MapBg, elevation = 4.dp) {
        IconButton(onClick = onClick) { Icon(icon, label, tint = Color.White) }
    }
}
