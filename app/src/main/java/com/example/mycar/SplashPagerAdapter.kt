package com.example.mycar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class SplashPagerAdapter(
    private val images: List<Int>,
    private val onNextClick: () -> Unit,  // Функция для кнопки "Дальше"
    private val onBackClick: () -> Unit,  // Функция для кнопки "Назад"
    private val onFinishClick: () -> Unit // Функция для кнопки "Перейти к приложению"
) : RecyclerView.Adapter<SplashPagerAdapter.SplashViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplashViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.splash_item, parent, false)
        return SplashViewHolder(view)
    }

    override fun onBindViewHolder(holder: SplashViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])

        // Настройка видимости кнопок в зависимости от текущей страницы
        when (position) {
            0 -> {
                holder.backButton.visibility = View.INVISIBLE  // На первом экране "Назад" не показывается
                holder.nextButton.text = "     "
                holder.nextButton.setOnClickListener {
                    onNextClick()
                }
            }
            images.size - 1 -> {
                holder.backButton.visibility = View.VISIBLE  // На последнем экране "Назад" показывается
                holder.backButton.setOnClickListener {
                    onBackClick()
                }
                holder.nextButton.text = "   "
                holder.nextButton.setOnClickListener {
                    onFinishClick()
                }
            }
            else -> {
                holder.backButton.visibility = View.VISIBLE
                holder.backButton.setOnClickListener {
                    onBackClick()
                }
                holder.nextButton.text = "       "
                holder.nextButton.setOnClickListener {
                    onNextClick()
                }
            }
        }
    }

    override fun getItemCount(): Int = images.size

    class SplashViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.splashImage)
        val backButton: Button = view.findViewById(R.id.backButton)
        val nextButton: Button = view.findViewById(R.id.nextButton)
    }
}
