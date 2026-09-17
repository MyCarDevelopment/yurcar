package com.example.mycar.ui.map

import com.yandex.mapkit.geometry.Point


data class CompanyInfo(
    val name: String,
    val services: String,
    val priceFrom: Int,
    val location: Point
)

val companies = listOf(
    CompanyInfo("Шиномонтаж 1", "Шиномонтаж, балансировка колес", 1000, Point(59.9342802, 30.3350986)),
    CompanyInfo("СТО 2", "Ремонт двигателя, замена масла", 2500, Point(59.9372802, 30.3380986)),
    CompanyInfo("Кузовные работы 3", "Покраска кузова, устранение вмятин", 3000, Point(59.9402802, 30.3400986)),
    CompanyInfo("Шиномонтаж 4", "Шиномонтаж, установка шин", 1200, Point(59.9422802, 30.3420986)),
    // Добавьте сюда остальные 36 компаний
)