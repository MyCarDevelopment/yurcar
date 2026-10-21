package com.example.mycar

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.mycar.ui.entry.*

class DocumentViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        configureEntryWindow(this)
        val document = allLegalDocuments.firstOrNull { it.fileName == intent.getStringExtra("fileName") }
        if (document == null) { finish(); return }
        setContent { AssetDocumentScreen(document, onClose = { finish() }) }
    }
}
