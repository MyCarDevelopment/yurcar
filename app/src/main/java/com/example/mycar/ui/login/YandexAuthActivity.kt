package com.example.mycar.ui.login

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.mycar.R

class YandexWebAuthActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnClose: Button
    private lateinit var titleText: TextView
    private val TAG = "YandexAuth"
    private var authComplete = false
    private val CLIENT_ID = "2aeed5b4dc494b3bb04ccdfb81945097"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_web_auth)

        Log.d(TAG, "🔥 Yandex OAuth started")
        initViews()
        setupWebView()
        loadYandexAuth()
    }

    private fun initViews() {
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        btnClose = findViewById(R.id.btnClose)
        titleText = findViewById(R.id.titleText)

        btnClose.setOnClickListener { finishWithCancel() }
    }

    private fun setupWebView() {
        val settings = webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            userAgentString = "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Mobile Safari/537.36"
            cacheMode = WebSettings.LOAD_NO_CACHE
        }

        webView.webChromeClient = WebChromeClient()

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(TAG, "Loading: $url")
                titleText.text = "Юркар • Авторизация"
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "Finished: $url | Title: ${webView.title}")
                progressBar.visibility = View.GONE
                titleText.text = "Юркар"
                url?.let { checkAuthResult(it) }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.e(TAG, "ERROR: ${error?.description}")
                progressBar.visibility = View.GONE
                titleText.text = "Ошибка сети"
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.url?.toString()?.let { url ->
                    Log.d(TAG, "Redirect: $url")
                    if (checkAuthResult(url)) return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().setAcceptCookie(true)
    }

    private fun loadYandexAuth() {
        val redirectUri = "https://oauth.yandex.ru/verification_code"
        val scopes = "login:email login:info"

        val authUrl = "https://oauth.yandex.ru/authorize?" +
                "client_id=$CLIENT_ID&" +
                "response_type=token&" +
                "redirect_uri=${android.net.Uri.encode(redirectUri)}&" +
                "scope=$scopes&" +
                "display=page"

        Log.d(TAG, "OAuth URL: $authUrl")
        webView.loadUrl(authUrl)
    }

    private fun checkAuthResult(url: String): Boolean {
        if (authComplete) return true

        Log.d(TAG, "Check result: $url")

        if (url.contains("access_token=")) {
            val token = url.substringAfter("access_token=").substringBefore("&").substringBefore("#")
            if (token.isNotEmpty()) {
                authComplete = true
                saveToken(token)
                successAuth(token)
                return true
            }
        } else if (url.contains("error=")) {
            val error = url.substringAfter("error=").substringBefore("&")
            authComplete = true
            cancelAuth("Ошибка: $error")
            return true
        }
        return false
    }

    private fun successAuth(token: String) {
        Log.d(TAG, "✅ SUCCESS: ${token.take(20)}...")
        runOnUiThread {
            Toast.makeText(this, "✅ Авторизация Юркар успешна!", Toast.LENGTH_LONG).show()
            val resultIntent = Intent().apply {
                putExtra("yandex_token", token)
                putExtra("auth_status", "success")
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun finishWithCancel() {
        val resultIntent = Intent().apply {
            putExtra("auth_status", "cancel")
        }
        setResult(Activity.RESULT_CANCELED, resultIntent)
        finish()
    }

    private fun cancelAuth(reason: String) {
        Log.e(TAG, "❌ $reason")
        runOnUiThread {
            Toast.makeText(this, reason, Toast.LENGTH_LONG).show()
            finishWithCancel()
        }
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("mycar_auth", MODE_PRIVATE)
        prefs.edit()
            .putString("yandex_token", token)
            .putBoolean("is_logged_in", true)
            .putLong("auth_time", System.currentTimeMillis())
            .commit()  // 🔥 commit() вместо apply() для гарантии
        Log.d(TAG, "✅ SAVED: is_logged_in=true, token=${token.take(10)}...")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
