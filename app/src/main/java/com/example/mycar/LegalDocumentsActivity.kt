package com.example.mycar

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycar.ui.entry.*

class LegalDocumentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        setContent { EntryTheme {
            Column(Modifier.fillMaxSize().background(EntryBg).systemBarsPadding()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { finish() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = EntryAccent) }
                    Text("Документы", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                }
                LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { Text("Все документы доступны здесь. Сам просмотр не означает согласия с ними.", color = EntryMuted, fontSize = 13.sp, lineHeight = 21.sp) }
                    items(allLegalDocuments, key = { it.id }) { document ->
                        Surface(Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).clickable {
                            startActivity(Intent(this@LegalDocumentsActivity, DocumentViewerActivity::class.java).putExtra("fileName", document.fileName))
                        }, color = EntrySurface, shape = RoundedCornerShape(18.dp)) {
                            Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Description, null, tint = EntryAccent, modifier = Modifier.size(22.dp))
                                Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                                    Text(document.title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                    Text(document.description, color = EntryMuted, fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 6.dp))
                                }
                                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = EntryMuted, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        } }
    }
}
