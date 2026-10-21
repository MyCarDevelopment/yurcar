package com.example.mycar.ui.crash

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mycar.R
import com.example.mycar.ui.entry.*
import com.example.mycar.ui.instructions.InstructionsActivity

class CrashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SosScreen(onDial = { dialNumber(context, it) }, onSource = { openSource(context, it) },
                    onOpenMap = { findNavController().navigate(R.id.navigation_dashboard) },
                    onRoadHelp = { startActivity(Intent(requireContext(), InstructionsActivity::class.java)) })
            }
        }
}

class SosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        setContent {
            Box(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
                SosScreen(onDial = { dialNumber(this@SosActivity, it) }, onSource = { openSource(this@SosActivity, it) },
                    onClose = { finish() }, onRoadHelp = { startActivity(Intent(this@SosActivity, InstructionsActivity::class.java)) })
            }
        }
    }
}

private fun dialNumber(context: Context, number: String) {
    try { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))) }
    catch (_: ActivityNotFoundException) { Toast.makeText(context, "Откройте телефон и наберите $number", Toast.LENGTH_LONG).show() }
}
private fun openSource(context: Context, url: String) {
    try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
    catch (_: ActivityNotFoundException) { Toast.makeText(context, "Не найден браузер", Toast.LENGTH_SHORT).show() }
}

@Composable
fun SosScreen(onDial: (String) -> Unit, onSource: (String) -> Unit,
              onOpenMap: (() -> Unit)? = null, onRoadHelp: () -> Unit = {}, onClose: (() -> Unit)? = null,
              initialTopic: String? = null) {
    var selectedId by rememberSaveable { mutableStateOf(initialTopic) }
    val selected = sosTopics.firstOrNull { it.id == selectedId }
    val urgent = Color(0xFFFFB3A5)
    BackHandler(enabled = selected != null) { selectedId = null }
    EntryTheme {
        Column(Modifier.fillMaxSize().background(EntryBg)) {
            if (selected != null || onClose != null) Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (selected != null) selectedId = null else onClose?.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = EntryAccent)
                }
                Text(if (selected != null) "SOS · ${selected.title}" else "Срочная помощь", color = Color.White,
                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            if (selected == null) LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("SOS", color = urgent, fontSize = 30.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        Text(" / Помощь в дороге", color = Color.White, fontSize = 15.sp, modifier = Modifier.padding(start = 10.dp))
                    }
                    Text("Сначала люди. Затем автомобиль.", color = EntryMuted, fontSize = 14.sp, modifier = Modifier.padding(top = 6.dp))
                }
                item {
                    Surface(color = Color(0xFF302022), shape = RoundedCornerShape(24.dp)) {
                        Column(Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.HealthAndSafety, null, tint = urgent, modifier = Modifier.size(30.dp))
                                Text("Есть угроза жизни?", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp))
                            }
                            Text("Пострадавшие, пожар, опасность на дороге — обратитесь в экстренные службы.", color = Color(0xFFD3BDBF),
                                fontSize = 13.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 12.dp))
                            Button(onClick = { onDial("112") }, colors = ButtonDefaults.buttonColors(backgroundColor = urgent, contentColor = EntryBg),
                                shape = RoundedCornerShape(15.dp), modifier = Modifier.fillMaxWidth().padding(top = 16.dp).heightIn(min = 54.dp)) {
                                Icon(Icons.Default.Phone, null, modifier = Modifier.size(21.dp)); Spacer(Modifier.width(10.dp))
                                Text("112 · Экстренная помощь", fontWeight = FontWeight.Bold)
                            }
                            Text("Кнопка откроет набор номера", color = Color(0xFFD3BDBF), fontSize = 10.sp, modifier = Modifier.padding(top = 9.dp))
                        }
                    }
                }
                item { Text("ЧТО ПРОИЗОШЛО?", color = EntryMuted, fontSize = 10.sp, letterSpacing = 1.4.sp) }
                items(sosTopics, key = { it.id }) { topic ->
                    val icon = when (topic.id) { "accident" -> Icons.Default.CarCrash; "breakdown" -> Icons.Default.CarRepair; "evacuated" -> Icons.Default.LocalShipping; else -> Icons.Default.FactCheck }
                    Surface(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).clickable { selectedId = topic.id }, color = EntrySurface,
                        shape = RoundedCornerShape(20.dp)) {
                        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(42.dp).background(EntryAccent.copy(alpha = .1f), RoundedCornerShape(13.dp)), contentAlignment = Alignment.Center) {
                                Icon(icon, null, tint = EntryAccent, modifier = Modifier.size(24.dp))
                            }
                            Column(Modifier.weight(1f).padding(horizontal = 13.dp)) {
                                Text(topic.title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                                Text(topic.summary, color = EntryMuted, fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 5.dp))
                            }
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = EntryMuted, modifier = Modifier.size(18.dp))
                        }
                    }
                }
                item {
                    Surface(color = EntrySurface, shape = RoundedCornerShape(20.dp)) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Эвакуация в Петербурге", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                            Text("Городская служба 004 подскажет, куда мог быть перемещён автомобиль.", color = EntryMuted,
                                fontSize = 12.sp, lineHeight = 20.sp, modifier = Modifier.padding(top = 8.dp))
                            OutlinedButton(onClick = { onDial("+78125764004") }, shape = RoundedCornerShape(13.dp),
                                modifier = Modifier.fillMaxWidth().padding(top = 12.dp).heightIn(min = 48.dp)) {
                                Icon(Icons.Default.Phone, null, modifier = Modifier.size(17.dp)); Text("  +7 (812) 576-40-04", fontSize = 13.sp)
                            }
                        }
                    }
                }
                item {
                    TextButton(onClick = onRoadHelp) { Text("Памятки по поломкам и помощи в пути") }
                    onOpenMap?.let { callback -> TextButton(onClick = callback) { Text("Открыть карту сервисов") } }
                    Text("Памятки помогают сориентироваться. В экстренной ситуации следуйте указаниям диспетчера и служб на месте.",
                        color = EntryMuted, fontSize = 11.sp, lineHeight = 18.sp, modifier = Modifier.padding(vertical = 10.dp))
                }
            } else key(selected.id) {
                LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    item {
                        Text(selected.title, color = Color.White, fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold)
                        Text(selected.summary, color = EntryMuted, fontSize = 14.sp, lineHeight = 22.sp, modifier = Modifier.padding(top = 10.dp))
                    }
                    item {
                        Surface(color = EntrySurface, shape = RoundedCornerShape(22.dp)) {
                            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                                Text("Действуйте по порядку", color = EntryAccent, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                                selected.steps.forEachIndexed { index, step -> Row {
                                    Text("0${index + 1}", color = EntryAccent, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.width(30.dp))
                                    Text(step, color = Color.White, fontSize = 14.sp, lineHeight = 23.sp)
                                } }
                            }
                        }
                    }
                    item {
                        Text(selected.checklistTitle, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        selected.checklist.forEach { point -> Text("•  $point", color = EntryMuted, fontSize = 14.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 12.dp)) }
                    }
                    item { Surface(color = Color(0xFF302022), shape = RoundedCornerShape(18.dp)) {
                        Text(selected.note, color = urgent, fontSize = 13.sp, lineHeight = 21.sp, modifier = Modifier.padding(18.dp))
                    } }
                    item {
                        if (selected.id == "accident") Button(onClick = { onDial("112") }, modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp), shape = RoundedCornerShape(14.dp)) { Text("Открыть набор 112") }
                        if (selected.id == "evacuated") Button(onClick = { onDial("+78125764004") }, modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp), shape = RoundedCornerShape(14.dp)) { Text("Позвонить в службу 004") }
                        TextButton(onClick = { onSource(selected.sourceUrl) }) { Text(selected.sourceTitle, fontSize = 12.sp) }
                    }
                }
            }
        }
    }
}
