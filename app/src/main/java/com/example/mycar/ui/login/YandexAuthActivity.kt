package com.example.mycar.ui.login

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mycar.R
import com.example.mycar.ui.entry.SessionRepository
import com.example.mycar.ui.entry.configureEntryWindow
import kotlinx.coroutines.launch

class YandexWebAuthActivity : AppCompatActivity() {
    private val model: YandexAuthViewModel by viewModels()
    private lateinit var webView: WebView
    private lateinit var progress: ProgressBar
    private lateinit var title: TextView
    private lateinit var retry: Button
    private lateinit var close: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SessionRepository(this).current() != null) { setResult(Activity.RESULT_OK); finish(); return }
        supportActionBar?.hide()
        configureEntryWindow(this)
        setContentView(R.layout.activity_yandex_web_auth)
        val root = findViewById<View>(R.id.auth_root)
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val padding = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            view.setPadding(padding.left, padding.top, padding.right, padding.bottom)
            insets
        }
        webView = findViewById(R.id.webView)
        progress = findViewById(R.id.progressBar)
        title = findViewById(R.id.titleText)
        close = findViewById(R.id.btnClose)
        retry = findViewById(R.id.btnRetry)
        close.setOnClickListener { finish() }
        retry.setOnClickListener { model.resetError(); loadAuthorization() }
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = false
            allowContentAccess = false
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        }
        CookieManager.getInstance().setAcceptCookie(true)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progress.progress = newProgress
                progress.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                title.text = "Яндекс ID"
                retry.visibility = View.GONE
            }
            override fun onPageFinished(view: WebView?, url: String?) { url?.let(::handleRedirect) }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url ?: return false
                if (handleRedirect(url.toString())) return true
                if (url.scheme != "https") { showError("Не удалось открыть этот способ входа"); return true }
                return false
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                if (request?.isForMainFrame == true) showError("Проверьте подключение к интернету")
            }
        }
        onBackPressedDispatcher.addCallback(this) {
            if (model.status.value == AuthSaveState.SAVING) return@addCallback
            if (webView.canGoBack()) webView.goBack() else finish()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.status.collect { status ->
                    close.isEnabled = status != AuthSaveState.SAVING
                    when (status) {
                        AuthSaveState.SAVED -> { setResult(Activity.RESULT_OK); finish() }
                        AuthSaveState.ERROR -> showError("Не удалось сохранить вход. Попробуйте снова")
                        AuthSaveState.SAVING -> { title.text = "Завершаем вход…"; retry.visibility = View.GONE }
                        else -> Unit
                    }
                }
            }
        }
        if (savedInstanceState == null || webView.restoreState(savedInstanceState) == null) loadAuthorization()
    }

    private fun loadAuthorization() {
        retry.visibility = View.GONE
        val url = Uri.parse("https://oauth.yandex.ru/authorize").buildUpon()
            .appendQueryParameter("client_id", "2aeed5b4dc494b3bb04ccdfb81945097")
            .appendQueryParameter("response_type", "token")
            .appendQueryParameter("redirect_uri", "https://oauth.yandex.ru/verification_code")
            .appendQueryParameter("scope", "login:email login:info")
            .appendQueryParameter("state", model.nonce).appendQueryParameter("display", "page").build()
        webView.loadUrl(url.toString())
    }

    private fun handleRedirect(url: String): Boolean = when (val result = parseYandexRedirect(url, model.nonce)) {
        null -> false
        YandexRedirect.Rejected -> { showError("Вход не подтверждён. Попробуйте ещё раз"); true }
        is YandexRedirect.Success -> { model.complete(result); true }
    }

    private fun showError(message: String) {
        title.text = message
        progress.visibility = View.GONE
        retry.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::webView.isInitialized) webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        if (::webView.isInitialized) { webView.stopLoading(); webView.destroy() }
        super.onDestroy()
    }
}
