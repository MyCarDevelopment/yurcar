package com.example.mycar.ui.mycarfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import com.example.mycar.BaseActivity
import com.example.mycar.ui.instructions.AccidentFragment

class AccidentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = "ДТП / Эвакуатор"
            setDisplayHomeAsUpEnabled(true)
        }

        setContentView(
            ComposeView(this).apply {
                setContent {
                    AccidentFragment()
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
