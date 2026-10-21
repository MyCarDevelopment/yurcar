package com.example.mycar.ui.marketplace

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.mycar.BaseActivity

class MarketplaceFragment : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent { ProductsScreen(onBack = { finish() }) }
    }
}
