package com.example.mycar.ui.map

enum class PointType(val label: String) {
    STO("СТО"), TIRE("Шиномонтаж"), BODYWORK("Кузовной ремонт"), POUND("Штрафстоянки")
}

data class MapPlace(
    val latitude: Double,
    val longitude: Double,
    val type: PointType,
    val companyName: String,
    val services: List<String>
)

fun filterMapPlaces(places: List<MapPlace>, query: String, types: Set<PointType>): List<MapPlace> {
    fun String.normalized() = lowercase(java.util.Locale.ROOT).replace('ё', 'е')
    val terms = query.normalized().trim().split(Regex("\\s+")).filter(String::isNotEmpty)
    return places.filter { place ->
        val text = (place.companyName + " " + place.type.label + " " + place.services.joinToString(" ")).normalized()
        (types.isEmpty() || place.type in types) && terms.all { it in text }
    }
}

val mapPlaces = listOf(

        MapPlace(
            latitude = 59.890000, longitude = 30.375000,
            type = PointType.POUND,
            companyName = "Штрафстоянка на Салова",
            services = listOf(
                "Спецстоянка",
                "Эвакуированные автомобили",
                "Санкт-Петербург, ул. Салова, 17"
            )
        ),
        MapPlace(
            latitude = 59.985500, longitude = 30.255000,
            type = PointType.POUND,
            companyName = "Штрафстоянка на Школьной",
            services = listOf(
                "Спецстоянка",
                "Хранение эвакуированных ТС",
                "Санкт-Петербург, ул. Школьная, 39"
            )
        ),
        MapPlace(
            latitude = 59.891800, longitude = 30.330500,
            type = PointType.POUND,
            companyName = "Штрафстоянка на Заставской",
            services = listOf(
                "Спецстоянка",
                "Выдача эвакуированных автомобилей",
                "Санкт-Петербург, Заставская ул., 4"
            )
        ),

        MapPlace(
            latitude = 59.990899, longitude = 30.205789,
            type = PointType.STO,
            companyName = "СТО на Савушкина",
            services = listOf(
                "Ремонт автомобилей",
                "Техническое обслуживание",
                "Санкт-Петербург, ул. Савушкина, 119 к.4"
            )
        ),
        MapPlace(
            latitude = 59.852971, longitude = 30.321510,
            type = PointType.STO,
            companyName = "СТО на Дунайском",
            services = listOf(
                "Диагностика",
                "ТО и ремонт подвески",
                "Санкт-Петербург, Дунайский пр., 15 к.2"
            )
        ),
        MapPlace(
            latitude = 59.800215, longitude = 30.325588,
            type = PointType.STO,
            companyName = "СТО на Пулковском",
            services = listOf(
                "Техническое обслуживание",
                "Ремонт авто",
                "Санкт-Петербург, Пулковское шоссе, 43 к.1"
            )
        ),
        MapPlace(
            latitude = 59.958820, longitude = 30.474233,
            type = PointType.STO,
            companyName = "СТО на Ириновском",
            services = listOf(
                "Обслуживание и ремонт",
                "Диагностика",
                "Санкт-Петербург, Ириновский пр., 10"
            )
        ),
        MapPlace(
            latitude = 59.984421, longitude = 30.252184,
            type = PointType.BODYWORK,
            companyName = "СТО на Школьной",
            services = listOf(
                "Кузовной ремонт",
                "Покраска и полировка",
                "Санкт-Петербург, Школьная, 73А"
            )
        ),
        MapPlace(
            latitude = 59.896421, longitude = 30.411287,
            type = PointType.STO,
            companyName = "СТО на Седова",
            services = listOf(
                "Ремонт автомобилей",
                "Шиномонтаж",
                "Санкт-Петербург, ул. Седова, 11АБ"
            )
        ),
        MapPlace(
            latitude = 60.037412, longitude = 30.347918,
            type = PointType.TIRE,
            companyName = "Колесо2 Северный",
            services = listOf(
                "Шиномонтаж 24/7",
                "Балансировка колес",
                "Санкт-Петербург, Северный пр., 32"
            )
        ),
        MapPlace(
            latitude = 59.964928, longitude = 30.487602,
            type = PointType.STO,
            companyName = "Сервис на Коммуны",
            services = listOf(
                "Авторемонт",
                "Техническое обслуживание",
                "Санкт-Петербург, ул. Коммуны, 16"
            )
        ),
        MapPlace(
            latitude = 59.873756, longitude = 30.378941,
            type = PointType.TIRE,
            companyName = "Шинсервис Фучика",
            services = listOf(
                "Шиномонтаж",
                "Балансировка",
                "Санкт-Петербург, ул. Фучика, 8"
            )
        ),
        MapPlace(
            latitude = 59.919460, longitude = 30.483250,
            type = PointType.TIRE,
            companyName = "Автостолица Дальневосточный",
            services = listOf(
                "Шиномонтаж",
                "ТО и мелкий ремонт",
                "Санкт-Петербург, Дальневосточный пр., 18А"
            )
        )
    )
