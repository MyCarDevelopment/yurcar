package com.example.mycar.ui.entry

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun ConsentScreen(documents: List<DocumentSnapshot>, loading: Boolean, saving: Boolean,
                  error: String?, onRetry: () -> Unit, onConfirm: (Set<String>) -> Unit, onBack: () -> Unit) {
    var checked by rememberSaveable { mutableStateOf(listOf<String>()) }
    var openedId by rememberSaveable { mutableStateOf<String?>(null) }
    val requiredCount = entryDocuments.count { it.required }
    val selectedRequired = entryDocuments.count { it.required && it.id in checked }
    BackHandler(enabled = saving) { }
    EntryTheme {
        Column(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, enabled = !saving) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = EntryAccent) }
                Text("Последний шаг", color = EntryMuted, fontSize = 13.sp)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.VerifiedUser, null, tint = EntryAccent, modifier = Modifier.padding(end = 22.dp).size(22.dp))
            }
            LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Text("Давайте договоримся", color = Color.White, fontSize = 29.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold)
                    Text("Прочитайте документы и отметьте свой выбор. Никаких заранее установленных галочек.", color = EntryMuted,
                        fontSize = 14.sp, lineHeight = 22.sp, modifier = Modifier.padding(top = 10.dp, bottom = 8.dp))
                }
                item { Text("ДЛЯ РАБОТЫ ПРИЛОЖЕНИЯ", color = EntryMuted, fontSize = 10.sp, letterSpacing = 1.4.sp) }
                items(entryDocuments.filter { it.required }, key = { it.id }) { document ->
                    ConsentChoice(document, document.id in checked, !saving && !loading,
                        onToggle = { checked = if (document.id in checked) checked - document.id else checked + document.id },
                        onOpen = { openedId = document.id })
                }
                item {
                    Text("ПО ВАШЕМУ ЖЕЛАНИЮ", color = EntryMuted, fontSize = 10.sp, letterSpacing = 1.4.sp,
                        modifier = Modifier.padding(top = 10.dp))
                    Text("Отказ от рассылок не ограничивает доступ к приложению.", color = EntryMuted,
                        fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 6.dp))
                }
                items(entryDocuments.filterNot { it.required }, key = { it.id }) { document ->
                    ConsentChoice(document, document.id in checked, !saving && !loading,
                        onToggle = { checked = if (document.id in checked) checked - document.id else checked + document.id },
                        onOpen = { openedId = document.id })
                }
                error?.let { message -> item {
                    Text(message, color = androidx.compose.ui.graphics.Color(0xFFFFB3A5), fontSize = 13.sp)
                    TextButton(onClick = onRetry, enabled = !saving) { Text("Попробовать снова") }
                } }
            }
            Surface(color = EntrySurface, elevation = 4.dp) {
                Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 14.dp)) {
                    Text(if (loading) "Загружаем документы…" else "Обязательные подтверждения: $selectedRequired из $requiredCount",
                        color = EntryMuted, fontSize = 12.sp, modifier = Modifier.padding(bottom = 10.dp))
                    Button(onClick = { onConfirm(checked.toSet()) }, enabled = !loading && !saving && error == null &&
                        documents.size == entryDocuments.size && requiredConsentsSelected(checked.toSet()),
                        shape = RoundedCornerShape(15.dp), modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)) {
                        if (saving) CircularProgressIndicator(Modifier.size(20.dp), color = EntryBg, strokeWidth = 2.dp)
                        else Text("Подтвердить и продолжить", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        documents.firstOrNull { it.document.id == openedId }?.let { snapshot ->
            Dialog(onDismissRequest = { openedId = null }, properties = DialogProperties(usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false)) {
                DocumentReaderScreen(snapshot.document.title, snapshot.text, null, onClose = { openedId = null })
            }
        }
    }
}

@Composable
private fun ConsentChoice(document: EntryDocument, checked: Boolean, enabled: Boolean,
                          onToggle: () -> Unit, onOpen: () -> Unit) {
    Surface(color = EntrySurface, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth().toggleable(checked, enabled = enabled, role = Role.Checkbox, onValueChange = { onToggle() }),
                verticalAlignment = Alignment.Top) {
                Checkbox(checked, onCheckedChange = null, enabled = enabled, modifier = Modifier.padding(top = 2.dp),
                    colors = CheckboxDefaults.colors(checkedColor = EntryAccent, uncheckedColor = EntryMuted, checkmarkColor = EntryBg))
                Column(Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(document.title, color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(document.description, color = EntryMuted, fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 5.dp))
                }
            }
            TextButton(onClick = onOpen, enabled = enabled, contentPadding = PaddingValues(horizontal = 0.dp, vertical = 6.dp),
                modifier = Modifier.padding(start = 36.dp).heightIn(min = 40.dp)) {
                Icon(Icons.Default.Description, null, modifier = Modifier.size(15.dp))
                Spacer(Modifier.width(6.dp)); Text("Прочитать документ", fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun AssetDocumentScreen(document: EntryDocument, onClose: () -> Unit) {
    val context = LocalContext.current
    var attempt by remember { mutableStateOf(0) }
    var text by remember(document.fileName) { mutableStateOf<String?>(null) }
    LaunchedEffect(document.fileName, attempt) {
        text = null
        text = withContext(Dispatchers.IO) {
            try { context.assets.open(document.fileName).bufferedReader().use { it.readText() } }
            catch (_: IOException) { "" }
        }
    }
    EntryTheme {
        DocumentReaderScreen(document.title, text, if (text == "") "Не удалось открыть документ" else null,
            onClose, onRetry = { attempt++ })
    }
}

@Composable
fun DocumentReaderScreen(title: String, text: String?, error: String?, onClose: () -> Unit, onRetry: () -> Unit = {}) {
    val paragraphs = remember(text) { text.orEmpty().split(Regex("\\n\\s*\\n")).filter { it.isNotBlank() } }
    Column(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
        Row(Modifier.fillMaxWidth().padding(end = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClose) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Вернуться к документам", tint = EntryAccent) }
            Text(title, color = androidx.compose.ui.graphics.Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
        when {
            text == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = EntryAccent) }
            error != null -> Column(Modifier.padding(24.dp)) {
                Text(error, color = EntryMuted)
                TextButton(onClick = onRetry) { Text("Повторить") }
            }
            else -> LazyColumn(contentPadding = PaddingValues(22.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(paragraphs.size) { index ->
                    Text(paragraphs[index], color = androidx.compose.ui.graphics.Color(0xFFD4D9DE), fontSize = 15.sp, lineHeight = 25.sp)
                }
            }
        }
    }
}
