package com.example.mycar.ui.mycarfragment


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mycar.BaseActivity
import com.example.mycar.R
import com.example.mycar.databinding.FragmentDashboardBinding
import com.example.mycar.ui.checkfines.CheckFinesFragment
import com.example.mycar.ui.indicators.IndicatorsActivity
import com.example.mycar.ui.instructions.InstructionsActivity
import com.example.mycar.ui.map.MapFragment
import java.io.File
import java.io.FileOutputStream

class MyCarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(
            ComposeView(this).apply {
                setContent {
                    MyCarScreen(
                        onBack = { finish() },
                        onTipsClick = { startActivity(Intent(this@MyCarActivity, UsefulTipsActivity::class.java)) },
                        onSymptomsClick = {
                            // ✅ Фрагмент по симптомам
                            startActivity(Intent(this@MyCarActivity, SymptomsActivity::class.java))
                        },
                        onLocationClick = {
                            // ✅ Фрагмент по местоположению
                            startActivity(Intent(this@MyCarActivity, LocationActivity::class.java))
                        },
                        onTiresClick = {
                            // ✅ Приборная панель
                            startActivity(Intent(this@MyCarActivity, IndicatorsActivity::class.java))
                        },
                        onCriticalityClick = {
                            // ✅ ✅ Фрагмент КРИТИЧНОСТЬ
                            startActivity(Intent(this@MyCarActivity, CriticalityActivity::class.java))
                        },
                        onQuickSolutionClick = {
                            // ✅ Быстрое решение
                            startActivity(Intent(this@MyCarActivity, InstructionsActivity::class.java))
                        },
                        onAccidentClick = {
                            // ✅ ДТП / Эвакуатор
                            startActivity(Intent(this@MyCarActivity, AccidentActivity::class.java))
                        }
                        //причина следств связей
                        //из чего состоит авто
                    )
                }
            }
        )
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}

