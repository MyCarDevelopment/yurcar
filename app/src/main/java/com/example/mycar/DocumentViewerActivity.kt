package com.example.mycar

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class DocumentViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val titleText = intent.getStringExtra("title") ?: "Документ"
        val fileName = intent.getStringExtra("fileName") ?: return

        supportActionBar?.apply {
            title = titleText
            setDisplayHomeAsUpEnabled(true)
        }

        val webView = WebView(this).apply {
            settings.javaScriptEnabled = false
            loadUrl("file:///android_asset/$fileName")
        }

        setContentView(webView)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}