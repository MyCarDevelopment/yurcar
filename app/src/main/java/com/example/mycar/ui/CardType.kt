package com.example.mycar.ui

import com.example.mycar.R

data class LegalDocument(
    val title: String,
    val fileName: String,
    val description: String
)

enum class CardType(val title: String, val icon: Int) {
    BUY_CAR("Справочник водителя", android.R.drawable.ic_dialog_info),
    SELL_CAR("Оплата штрафов", R.drawable.ic_sell),
    TIRES("Приборная панель", R.drawable.ic_tire),
    SERVICES("Услуги", R.drawable.ic_service),
    INSTRUCTIONS("Помощь на дороге", R.drawable.ic_inst),
    SIXTH("Товары", R.drawable.ic_news)
}
val topGridCards = listOf(
    CardType.BUY_CAR,
    CardType.TIRES,
    CardType.INSTRUCTIONS
)

val bottomGridCards = listOf(
    CardType.SERVICES,
    CardType.SIXTH
)

val rectangleCard = CardType.SELL_CAR

data class Service(
    val name: String,
    val companies: List<Company>,
    val imageResId: Int
)

data class Company(
    val name: String,
    val address: String,
    val phone: String,
    val services: String
)
val services = listOf(
    Service(
        name = "Шины",
        companies = listOf(
            Company(
                name = "ШинСервис",
                address = "ул. Новая, д. 1",
                phone = "+7 123 456 7890",
                services = "Переобувка, балансировка, ремонт шин"
            ),
            Company(
                name = "ШинЭксперт",
                address = "ул. Ленина, д. 3",
                phone = "+7 987 123 4567",
                services = "Балансировка, замена шин"
            ),
            Company(
                name = "ШинСервис",
                address = "ул. Новая, д. 1",
                phone = "+7 123 456 7890",
                services = "Переобувка, балансировка, ремонт шин"
            ),
            Company(
                name = "ШинЭксперт",
                address = "ул. Ленина, д. 3",
                phone = "+7 987 123 4567",
                services = "Балансировка, замена шин"
            ),
            Company(
                name = "ШинСервис",
                address = "ул. Новая, д. 1",
                phone = "+7 123 456 7890",
                services = "Переобувка, балансировка, ремонт шин"
            ),
            Company(
                name = "ШинЭксперт",
                address = "ул. Ленина, д. 3",
                phone = "+7 987 123 4567",
                services = "Балансировка, замена шин"
            ),
            Company(
                name = "ШинСервис",
                address = "ул. Новая, д. 1",
                phone = "+7 123 456 7890",
                services = "Переобувка, балансировка, ремонт шин"
            ),
            Company(
                name = "ШинЭксперт",
                address = "ул. Ленина, д. 3",
                phone = "+7 987 123 4567",
                services = "Балансировка, замена шин"
            )

        ),
        imageResId = R.drawable.pngtyres
    ),
    Service(
        name = "Кузовные работы",
        companies = listOf(
            Company(
                name = "КузовМастер",
                address = "ул. Богатырская, д. 2",
                phone = "+7 987 654 3210",
                services = "Ремонт кузова, покраска, устранение вмятин"
            )
        ),
        imageResId = R.drawable.specialservice1
    ),
    Service(
        name = "СТО",
        companies = listOf(
            Company(
                name = "АвтоТех",
                address = "ул. Никольская, д. 3",
                phone = "+7 555 666 7777",
                services = "Техосмотр, диагностика, ремонт"
            )
        ),
        imageResId = R.drawable.specialservice2
    )
)


data class Story(
    val title: String,
    val previewImageRes: Int,  // Превью фото
    val contentImageRes: Int  // Основное изображение
)

