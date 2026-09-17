package com.example.mycar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mycar.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val images = listOf(R.drawable.sfirst, R.drawable.ssecond, R.drawable.sthird)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            setContentView(R.layout.activity_splash)

            val viewPager = findViewById<ViewPager2>(R.id.viewPager)
            val adapter = SplashPagerAdapter(
                images,
                onNextClick = {
                    val currentItem = viewPager.currentItem
                    if (currentItem < images.size - 1) {
                        viewPager.currentItem = currentItem + 1
                    }
                },
                onBackClick = {
                    val currentItem = viewPager.currentItem
                    if (currentItem > 0) {
                        viewPager.currentItem = currentItem - 1
                    }
                },
                onFinishClick = {
                    // Сохраняем, что это не первый запуск
                    sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()

                    // Переход в MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            viewPager.adapter = adapter
        } else {
            // Если это не первый запуск, сразу переходим в MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


