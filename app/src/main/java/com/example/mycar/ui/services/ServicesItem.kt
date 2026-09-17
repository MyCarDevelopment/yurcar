package com.example.mycar.ui.services

import androidx.compose.ui.graphics.Color
import com.example.mycar.R


data class Service(
    val id: Int,
    val title: String,
    val description: String,
    val companies: List<Company>,
    val imageResId: Int
)

data class Company(
    val name: String,
    val address: String,
    val phone: String, //дилит
    val workingHours: String,
    val services: String, //дилит
    val price: String, //дилит
    val metroStation: String,
    val metroColor: Color
)

val services = listOf(
    Service(
        id = 1,
        title = "Шиномонтаж",
        description = "Список компаний, осуществляющих шиномонтаж",
        imageResId = R.drawable.tyrechange,
        companies = listOf(
            Company(
                name = "Pit-Stop",
                address = "Санкт-Петербург, улица Шелгунова, 3",
                phone = "+7 (123) 456-78-90",
                workingHours = "10:00 - 23:00",
                services = "Замена и балансировка шин",
                metroStation = "Горьковская",
                metroColor = Color.Blue,
                price = "от 500 руб."
            ),
            Company(
                name = "Лангустин",
                address = "Санкт-Петербург, улица Руставели, 43",
                phone = "+7 (123) 555-11-22",
                workingHours = "10:00 - 19:00",
                services = "Ремонт и балансировка",
                metroStation = "Девяткино",
                metroColor = Color.Red,
                price = "от 700 руб."
            ),
            Company(
                name = "Urban",
                address = "Санкт-Петербург, улица Ново-Александровская , 24",
                phone = "+7 (123) 555-11-22",
                workingHours = "10:00 - 19:00",
                services = "Ремонт и балансировка",
                metroStation = "Улица Дыбенко",
                metroColor = Color.Yellow,
                price = "от 700 руб."
            ),
            Company(
                name = "Frigat",
                address = "Санкт-Петербург, улица Ивановская, 6",
                phone = "+7 (123) 555-11-22",
                workingHours = "10:00 - 19:00",
                services = "Ремонт и балансировка",
                metroStation = "Невский Проспект",
                metroColor = Color.Blue,
                price = "от 700 руб."
            ),
            Company(
                name = "Колёса Даром",
                address = "Санкт-Петербург, улица Софийская, 8, корп. 1, стр. 3",
                phone = "+7 (123) 555-11-22",
                workingHours = "10:00 - 19:00",
                services = "Ремонт и балансировка",
                metroStation = "Елизаровская",
                metroColor = Color.Green,
                price = "от 700 руб."
            )
        )
    ),
    Service(
        id = 2,
        title = "Кузовные работы",
        description = "Список компаний, осуществляющие кузовные работы",
        imageResId = R.drawable.kuzwork,
        companies = listOf(
            Company(
                name = "Онлишкода",
                address = "Боровая ул., 55, корп. 2, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:30 - 20:00 пн-сб",
                services = "Ремонт кузова, покраска",
                metroStation = "Обводный канал",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "Вояж",
                address = "Рощинская ул., 1, корп. 3Д, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 20:00",
                services = "Ремонт кузова, покраска",
                metroStation = "Электросила",
                metroColor = Color.Blue,
                price = "от 2000 руб."
            ),
            Company(
                name = "АвтоХаб МК",
                address = "Курская ул., 28Л, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 19:00 пн-пт",
                services = "Ремонт кузова, покраска",
                metroStation = "Обводный канал",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "Salad Conversions",
                address = "Боровая ул., 55, корп. 3, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "11:00 - 20:00 пн-пт",
                services = "Ремонт кузова, покраска",
                metroStation = "Обводный канал",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "Формула ремонта",
                address = "ул. Салова, 57, корп. 1, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "09:00 - 21:00 пн-пт",
                services = "Ремонт кузова, покраска",
                metroStation = "Бухарестская",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "Вмятинов",
                address = "ул. Розенштейна, 21, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "11:00 - 19:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Балтийская",
                metroColor = Color.Red,
                price = "от 2000 руб."
            ),
            Company(
                name = "Вмятинов",
                address = "ул. Седова, 12, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 19:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Елизаровская",
                metroColor = Color.Green,
                price = "от 2000 руб."
            ),
            Company(
                name = "Profim Pdr",
                address = "Витебский просп., 1А, стр. 1, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 19:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Электросила",
                metroColor = Color.Blue,
                price = "от 2000 руб."
            ),
            Company(
                name = "Авторум",
                address = "ул. Ольги Берггольц, 36, стр. 1, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 21:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Елизаровская",
                metroColor = Color.Green,
                price = "от 2000 руб."
            ),
            Company(
                name = "МК центр",
                address = "ул. Седова, 10, корп. 2Т, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 20:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Елизаровская",
                metroColor = Color.Green,
                price = "от 2000 руб."
            ),
            Company(
                name = "АвтоЭстетика",
                address = "наб. Обводного канала, 90Ж, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 20:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Обводный канал",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "АйБрокер",
                address = "Боровая ул., 55, корп. 3, Санкт-Петербург",
                phone = "+7 (123) 999-99-99",
                workingHours = "10:00 - 21:00 пн-вс",
                services = "Ремонт кузова, покраска",
                metroStation = "Обводный канал",
                metroColor = Color(0xFF800080),
                price = "от 2000 руб."
            ),
            Company(
                name = "Малярка №7",
                address = "Московский просп., 83Д, Санкт-Петербург",
                phone = "+7 (812) 995-61-39",
                workingHours = "10:00–21:00 пн-вс",
                services = "Кузовные работы",
                price = "от 5300 руб.",
                metroStation = "Московская",
                metroColor = Color.Green
            ),
            Company(
                name = "Синдикат",
                address = "Воздухоплавательная ул., 11, Санкт-Петербург",
                phone = "+7 (812) 995-61-40",
                workingHours = "10:00–20:00 пн-сб",
                services = "Кузовные работы",
                price = "от 5400 руб.",
                metroStation = "Политехническая",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Автопаинт",
                address = "просп. Обуховской Обороны, 51В, Санкт-Петербург",
                phone = "+7 (812) 995-61-41",
                workingHours = "10:00–20:00 пн-сб",
                services = "Кузовные работы",
                price = "от 5500 руб.",
                metroStation = "Площадь Ленина",
                metroColor = Color.Red
            ),
            Company(
                name = "CarBoneService",
                address = "наб. реки Волковки, 15Д, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–20:00 пн-вс",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Гайде",
                address = "Боровая ул., 53, корп. 4, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–18:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Центр кузовного ремонта",
                address = "Лиговский просп., 246Б, Санкт-Петербург",
                phone = "",
                workingHours = "09:00–21:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Дженерал Карс",
                address = "наб. Обводного канала, 64, корп. 2Д, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–18:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "AutoStyle",
                address = "ул. Салова, 36, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–20:00 пн-вс",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "ДефектовНет",
                address = "Смоляная ул., 21, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–20:00 пн-вс",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Автоблеск",
                address = "наб. Обводного канала, 90, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–20:00 пн-вс",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Garage-G1",
                address = "ул. Салова, 31, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–20:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "RR Performance Laser",
                address = "ул. Профессора Качалова, 9Л, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–13:00, 14:00–22:00 пн-сб",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Fix4Car",
                address = "Бухарестская ул., 6, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–21:00",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "7 Мастеров",
                address = "Софийская ул., 2Б, Санкт-Петербург",
                phone = "",
                workingHours = "09:00–21:00",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Garage-G1",
                address = "Бухарестская ул., 14, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–18:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Шеви Плюс",
                address = "Заставская ул., 15Д, Санкт-Петербург",
                phone = "",
                workingHours = "09:00–20:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Субарубалт",
                address = "Грузинская ул., 3, корп. 1, Санкт-Петербург",
                phone = "",
                workingHours = "09:00–21:00 пн-вс",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "СДжойнт Моторс",
                address = "Хрустальная ул., 31З, Санкт-Петербург",
                phone = "",
                workingHours = "10:00–21:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Boom Car Studio",
                address = "ул. Бехтерева, 2, Санкт-Петербург",
                phone = "",
                workingHours = "11:00–21:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Автопластик",
                address = "Камчатская ул., 19Ж, Санкт-Петербург",
                phone = "",
                workingHours = "11:00–18:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Кузов Маркет",
                address = "ул. Седова, 5, Санкт-Петербург",
                phone = "",
                workingHours = "11:00–18:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Нева МКЦ",
                address = "ул. Салова, 70А, Санкт-Петербург",
                phone = "",
                workingHours = "09:00–21:00 пн-пт",
                services = "Кузовные работы",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            )
        )
    ),
    Service(
        id = 3,
        title = "Эвакуаторы",
        description = "Заказ эвакуатора по Санкт-Петербургу",
        imageResId = R.drawable.evacuator,
        companies = listOf(
            Company(
                name = "Топтим555",
                address = "Благодатная ул., 65, Санкт-Петербург",
                phone = "+7 (911) 118-05-55",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1500 руб.",
                metroStation = "Московская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Автоклуб А24",
                address = "Витебский просп., 11, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 237-30-84",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1700 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Green
            ),
            Company(
                name = "VIP Эвакуатор 24 часа",
                address = "просп. Стачек, 60, Санкт-Петербург",
                phone = "+7 (921) 938-07-05",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2000 руб.",
                metroStation = "Кировский завод",
                metroColor = Color.Red
            ),
            Company(
                name = "Самурай",
                address = "ул. Трефолева, 4, корп. 1, Санкт-Петербург",
                phone = "+7 (952) 266-07-63",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1800 руб.",
                metroStation = "Нарвская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Техноколор",
                address = "Волковский просп., 62, Санкт-Петербург",
                phone = "+7 (981) 772-53-19",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1900 руб.",
                metroStation = "Волковская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Помощь на дороге",
                address = "ул. Салова, 29, Санкт-Петербург",
                phone = "+7 (812) 628-27-34",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1700 руб.",
                metroStation = "Бухарестская",
                metroColor = Color.Red
            ),
            Company(
                name = "Мотовоз",
                address = "Заставская ул., 4, Санкт-Петербург",
                phone = "+7 (812) 340-00-15",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1800 руб.",
                metroStation = "Московские ворота",
                metroColor = Color.Green
            ),
            Company(
                name = "Автоспас",
                address = "Бухарестская ул., 1Б, Санкт-Петербург",
                phone = "+7 (812) 911-07-00",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2000 руб.",
                metroStation = "Бухарестская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Авто-БУлавка",
                address = "Рощинская ул., 2Л, Санкт-Петербург",
                phone = "+7 (931) 258-51-58",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1900 руб.",
                metroStation = "Электросила",
                metroColor = Color.Blue
            ),
            Company(
                name = "Друг",
                address = "ул. Фучика, 23, корп. 1, Санкт-Петербург",
                phone = "+7 (952) 269-22-66",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2000 руб.",
                metroStation = "Ломоносовская",
                metroColor = Color.Red
            ),
            Company(
                name = "Нева",
                address = "ул. Ивана Черных, 20, Санкт-Петербург",
                phone = "+7 (911) 817-65-73",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1900 руб.",
                metroStation = "Нарвская",
                metroColor = Color.Green
            ),
            Company(
                name = "Эватакси",
                address = "ул. Трефолева, 4, корп. 1, Санкт-Петербург",
                phone = "+7 (952) 222-04-44",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2000 руб.",
                metroStation = "Нарвская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Спасмобиль",
                address = "Бассейная ул., 38, Санкт-Петербург",
                phone = "+7 (812) 977-00-00",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2200 руб.",
                metroStation = "Московская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Автоспасение24",
                address = "Митрофаньевское ш., 5ЕБ, Санкт-Петербург",
                phone = "+7 (495) 147-96-54",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2500 руб.",
                metroStation = "Фрунзенская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Эвакуация Экспресс",
                address = "ул. Маршала Говорова, 29Х, Санкт-Петербург",
                phone = "+7 (988) 194-77-45",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2300 руб.",
                metroStation = "Кировский завод",
                metroColor = Color.Green
            ),
            Company(
                name = "Спасение на дороге",
                address = "Цветочная ул., 16К, Санкт-Петербург",
                phone = "+7 (938) 111-06-36",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2000 руб.",
                metroStation = "Фрунзенская",
                metroColor = Color.Red
            ),
            Company(
                name = "Мастер ключ",
                address = "Московский просп., 171, Санкт-Петербург",
                phone = "+7 (812) 339-29-29",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 2100 руб.",
                metroStation = "Парк Победы",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Первый Автосервис",
                address = "ул. Салова, 70А, Санкт-Петербург",
                phone = "+7 (812) 985-35-33",
                workingHours = "Круглосуточно",
                services = "Эвакуация автомобилей",
                price = "от 1900 руб.",
                metroStation = "Бухарестская",
                metroColor = Color.Green
            )
        )
    ),
    Service(
        id = 4,
        title = "Автоподбор",
        description = "Список автоподборщиков",
        imageResId = R.drawable.avtopodbor,
        companies = listOf(
            Company(
                name = "Spb-Cars",
                address = "ул. Профессора Качалова, 9, Санкт-Петербург, этаж 2, офис 203",
                phone = "+7 (812) 205-98-98",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "ДСС Групп Import",
                address = "Софийская ул., 8, корп. 1, стр. 2, Санкт-Петербург, этаж 2, офис 203",
                phone = "+7 (930) 600-80-96",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "OMGauto",
                address = "ул. Маршала Казакова, 35Р, Санкт-Петербург",
                phone = "+7 (812) 467-41-25",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "ЭкспертАвто",
                address = "просп. Стачек, 92, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 943-00-92",
                workingHours = "09:00 – 21:00",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Dss academy",
                address = "Софийская ул., 8, корп. 1, стр. 4, Санкт-Петербург",
                phone = "+7 (958) 523-91-23",
                workingHours = "09:00 – 21:00",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Км Подбор",
                address = "ул. Салова, 38, корп. 3, Санкт-Петербург",
                phone = "+7 (931) 534-65-33",
                workingHours = "10:00 – 21:00",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Golden Key",
                address = "Софийская ул., 6Б, Санкт-Петербург",
                phone = "+7 (812) 923-93-83",
                workingHours = "09:00 – 21:00",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "CarsKorea",
                address = "ул. Седова, 11, Санкт-Петербург",
                phone = "+7 (921) 999-47-45",
                workingHours = "09:00 – 21:00",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "WestMotors",
                address = "ул. Фучика, 4К, Санкт-Петербург",
                phone = "+7 (981) 914-70-27",
                workingHours = "10:00 – 19:00 пн-сб",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Ювентус",
                address = "Кронштадтская ул., 17, корп. 2, Санкт-Петербург",
                phone = "+7 (911) 828-04-04",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Mint Condition",
                address = "Средняя ул., 6, Санкт-Петербург",
                phone = "+7 (921) 307-84-48",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "ДальАвто",
                address = "Варфоломеевская ул., 6, Санкт-Петербург",
                phone = "+7 (969) 966-75-28",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "ИнфоАвто",
                address = "7-я Советская ул., 16, Санкт-Петербург",
                phone = "8 (800) 707-44-01",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Честный Автоподбор",
                address = "ул. Оптиков, 1, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 270-26-21",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Автоподбор NewCarSPB",
                address = "Екатерининский просп., 5АБ, Санкт-Петербург",
                phone = "+7 (931) 222-50-57",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "АвтоПодбор812",
                address = "ул. Чапаева, 9, Санкт-Петербург",
                phone = "+7 (812) 955-54-45",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "ClinliCar",
                address = "Большой Сампсониевский просп., 61, Санкт-Петербург",
                phone = "+7 (812) 200-95-63",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Егор-АвтоПодбор",
                address = "2-й Муринский просп., 36, Санкт-Петербург",
                phone = "8 (800) 707-49-53",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Правильный Подбор",
                address = "Комендантский просп., 4, корп. 2, лит.А, Санкт-Петербург",
                phone = "+7 (911) 190-62-25",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "CarSpace",
                address = "ш. Революции, 69, Санкт-Петербург",
                phone = "+7 (904) 613-25-97",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Mclubauto",
                address = "Киришская ул., 2А, Санкт-Петербург",
                phone = "+7 (960) 232-18-29",
                workingHours = "",
                services = "Автоподбор",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            )
        )
    ),
    Service(
        id = 5,
        title = "Замена стекол",
        description = "Список компаний, осуществляющие замену стекол",
        imageResId = R.drawable.zamenasteklo,
        companies = listOf(
            Company(
                name = "Jjglass",
                address = "ул. Фучика, 12, корп. 3, Санкт-Петербург",
                phone = "+7 (911) 137-68-47, +7 (812) 708-82-82",
                workingHours = "ежедневно, 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Авто-Хэлп",
                address = "ул. Салова, 76, Санкт-Петербург",
                phone = "+7 (812) 363-00-00",
                workingHours = "ежедневно, 10:00–18:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "SaveCars",
                address = "Бухарестская ул., 1Б, Санкт-Петербург",
                phone = "+7 (812) 250-47-68",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "Волковская, Бухарестская",
                metroColor = Color.Green
            ),
            Company(
                name = "Bitstop",
                address = "Южное ш., 37, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 770-47-43",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Автотрейд",
                address = "Рабочая ул., 7Ж, исторический район Старо-Паново, Красносельский район, Санкт-Петербург",
                phone = "8 (800) 555-54-15, +7 (812) 604-77-26",
                workingHours = "ежедневно, 09:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Bitstop",
                address = "5-й Предпортовый пр., 14, корп. 1, Санкт-Петербург",
                phone = "8 (800) 600-91-67, +7 (812) 770-44-30",
                workingHours = "пн-сб 10:00–20:00; вс 10:00–18:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Carglass",
                address = "ул. Салова, 70, Санкт-Петербург",
                phone = "+7 (812) 770-63-24",
                workingHours = "вт-сб 09:00–18:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Центр автостёкол",
                address = "Софийская ул., 8, корп. 1, стр. 1, Санкт-Петербург",
                phone = "+7 (812) 984-90-80",
                workingHours = "пн-сб 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Avtoglass",
                address = "ул. Возрождения, 20, Санкт-Петербург",
                phone = "+7 (921) 906-27-63, +7 (812) 906-27-63",
                workingHours = "пн-пт 10:00–17:45",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Pro-Avtosteklo",
                address = "Лиговский просп., 153, Санкт-Петербург (паркинг 5 уровень, ТЦ ЛиговЪ, этаж 9)",
                phone = "+7 (921) 907-49-08",
                workingHours = "ежедневно, 09:00–22:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Хэлп Стекло",
                address = "просп. Маршала Жукова, 21В, Санкт-Петербург",
                phone = "+7 (921) 942-72-04, +7 (812) 982-80-93",
                workingHours = "вт-сб 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Моё Автостекло",
                address = "ул. Крыленко, 2А, Санкт-Петербург",
                phone = "+7 (812) 989-82-98",
                workingHours = "пн-сб 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "АвтоТриплекс",
                address = "просп. Народного Ополчения, 201, Санкт-Петербург (секция 2-9)",
                phone = "+7 (812) 988-29-29",
                workingHours = "ежедневно, 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Центр Автостекла",
                address = "Смоляная ул., 12, корп. 2В, Санкт-Петербург",
                phone = "+7 (812) 972-15-14, +7 (812) 904-52-32, +7 (921) 904-52-32",
                workingHours = "пн-пт 10:00–19:00; сб 10:00–16:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Стекломакс",
                address = "Дальневосточный просп., 20, корп. 2, Санкт-Петербург",
                phone = "+7 (951) 646-01-96",
                workingHours = "пн-пт 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "AG Experts",
                address = "Цветочная ул., 16, Санкт-Петербург",
                phone = "8 (800) 700-88-77, +7 (812) 223-43-90",
                workingHours = "вт-сб 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Ваше Стекло",
                address = "просп. Юрия Гагарина, 32, корп. 6Б, Санкт-Петербург",
                phone = "+7 (921) 322-79-57, +7 (812) 407-11-81",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Хоттабыч-Авто",
                address = "просп. Юрия Гагарина, 32, корп. 6Б, Санкт-Петербург",
                phone = "+7 (812) 986-64-70, +7 (931) 221-22-31",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Лобовух",
                address = "Пулковское ш., 14, стр. 6, Санкт-Петербург",
                phone = "+7 (921) 657-89-93",
                workingHours = "ежедневно, 10:00–22:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Автостекло",
                address = "Смоляная ул., 12, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 309-85-83, +7 (921) 371-82-38",
                workingHours = "пн-сб 10:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Центр Автостекла Роберта Романова",
                address = "Бухарестская ул., 10, стр. 1, Санкт-Петербург (этаж 1)",
                phone = "+7 (911) 929-00-82, +7 (921) 907-34-77",
                workingHours = "ежедневно, 10:00–18:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "SQCar",
                address = "Лиговский просп., 246Б, Санкт-Петербург (1)",
                phone = "+7 (911) 920-64-07, +7 (821) 558-02-98",
                workingHours = "пн-пт 09:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Автотрейд",
                address = "Софийская ул., 2Б, Санкт-Петербург",
                phone = "8 (800) 555-54-15, +7 (812) 604-77-26",
                workingHours = "ежедневно, 09:00–19:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Fuyao",
                address = "ул. Шкапина, 51Б, Санкт-Петербург",
                phone = "+7 (981) 192-80-15",
                workingHours = "пн-пт 10:00–19:00; сб 10:00–13:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "За стеклом",
                address = "ул. Крыленко, 2АБ, Санкт-Петербург",
                phone = "+7 (812) 989-51-13, +7 (952) 289-51-13",
                workingHours = "пн-сб 10:00–19:00, перерыв 14:00–15:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Сколовичкофф",
                address = "ул. Типанова, 27/39, Санкт-Петербург (въезд в подземный паркинг)",
                phone = "+7 (901) 132-35-50",
                workingHours = "ежедневно, 10:00–22:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Замена автостекла",
                address = "Шкиперский проток, 14, корп. 16И, Санкт-Петербург",
                phone = "+7 (911) 928-00-98, +7 (921) 907-25-74",
                workingHours = "пн-пт 10:00–20:00; сб 11:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Ремонт стекол",
                address = "ул. Седова, 12, Санкт-Петербург",
                phone = "+7 (981) 787-58-97",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Avtofasad",
                address = "ул. Руставели, 46, корп. 1, Санкт-Петербург",
                phone = "+7 (921) 956-75-70, +7 (905) 260-18-03",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена автостекол",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            )
        )
    ),
    Service(
        id = 6,
        title = "Ремонт двигателей",
        description = "Список компаний, осуществляющие ремонт и диагностику ДВС",
        imageResId = R.drawable.dvs,
        companies = listOf(
            Company(
                name = "Эстакада",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (966) 014-22-66",
                workingHours = "10:00 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Точка Vag",
                address = "Курская ул., 28Л, Санкт-Петербург",
                phone = "+7 (981) 999-64-55",
                workingHours = "",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Сервис Мерседес Пик",
                address = "Боровая ул., 116, Санкт-Петербург",
                phone = "+7 (812) 917-42-98",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Онлишкода",
                address = "Боровая ул., 55, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 993-78-38",
                workingHours = "10:30 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Ниссан Гермес",
                address = "наб. Обводного канала, 150Р, Санкт-Петербург",
                phone = "+7 (812) 615-85-10",
                workingHours = "10:00 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "ПрофАвто",
                address = "Курская ул., 28/32В, Санкт-Петербург",
                phone = "+7 (812) 237-31-69",
                workingHours = "10:00 – 22:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Сервис BMW Пик",
                address = "Кушелевская дорога, 20, Санкт-Петербург",
                phone = "+7 (812) 910-52-19",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Масленка",
                address = "Дальневосточный просп., 13, Санкт-Петербург",
                phone = "+7 (901) 132-87-01",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "Улица Дыбенко",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Виктори Моторс",
                address = "ул. Осипенко, 2, Санкт-Петербург",
                phone = "+7 (812) 240-87-352, +7 (952) 277-35-95",
                workingHours = "10:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "АвтоРесурс",
                address = "Литовская ул., 17А, Санкт-Петербург",
                phone = "+7 (911) 945-07-07",
                workingHours = "10:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "ДжиМоторс",
                address = "Транспортный пер., 10М, Санкт-Петербург",
                phone = "+7 (812) 240-80-36",
                workingHours = "10:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Механика СПб",
                address = "ш. Революции, 84З, Санкт-Петербург",
                phone = "+7 (812) 622-08-12",
                workingHours = "08:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Мотор Плюс",
                address = "Глухоозёрское ш., 2А, Санкт-Петербург",
                phone = "+7 (812) 600-70-40",
                workingHours = "10:00 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Пассат-Энерго",
                address = "ул. Комсомола, 13, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 542-02-25",
                workingHours = "08:00 – 17:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "СТО 1 Автосервис",
                address = "Минеральная улица, Санкт-Петербург",
                phone = "+7 (921) 638-45-45",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Опель сервис",
                address = "ул. Профессора Качалова, 21Д, Санкт-Петербург",
                phone = "+7 (812) 903-65-05",
                workingHours = "10:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "M50",
                address = "Миргородская ул., 30, Санкт-Петербург",
                phone = "+7 (812) 611-23-11",
                workingHours = "10:00 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "ФТ-Сервис",
                address = "Лабораторный просп., 18, корп. 4, Санкт-Петербург",
                phone = "+7 (812) 303-66-663, +7 (963) 304-66-66, +7 (952) 366-66-40",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Автосервис Гольф-Клуб",
                address = "ул. Александра Матросова, 4, корп. 2АЖ, Санкт-Петербург",
                phone = "+7 (812) 237-31-79",
                workingHours = "10:00 – 19:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Пятая скорость",
                address = "17-я линия Васильевского острова, 54, корп. 2Е, Санкт-Петербург",
                phone = "+7 (812) 986-00-05",
                workingHours = "10:00 – 19:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "КСАвтоСервис",
                address = "Рижский просп., 37, лит.З, Санкт-Петербург",
                phone = "+7 (812) 251-58-62",
                workingHours = "09:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Бомба-Сервис",
                address = "Бобруйская ул., 11Б, Санкт-Петербург",
                phone = "+7 (812) 974-02-16",
                workingHours = "10:30 – 20:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "AutoComplex78",
                address = "Лиговский просп., 60-62АБ, Санкт-Петербург",
                phone = "+7 (812) 214-43-13",
                workingHours = "10:00 – 21:00",
                services = "Ремонт двигателей",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            )
        )
    ),
    Service(
        id = 7,
        title = "Автодиагностика",
        description = "Список компаний, проводящую автодиагностику Вашего авто",
        imageResId = R.drawable.diagnostick,
        companies = listOf(
            Company(
                name = "Сити Дизель",
                address = "Хрустальная ул., 31, лит.О, Санкт-Петербург",
                phone = "+7 (901) 132-43-87",
                workingHours = "09:30 – 20:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Купчино Трансмишн",
                address = "ул. Салова, 47, Санкт-Петербург (помещение 4H, 11Н)",
                phone = "+7 (812) 240-84-38",
                workingHours = "09:30 – 20:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "АнтиДилер",
                address = "ул. Ольги Берггольц, 36, корп. 3, Санкт-Петербург",
                phone = "+7 (921) 656-11-26",
                workingHours = "10:00 – 21:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Mercedes-78",
                address = "ул. Бехтерева, 1, корп. 4, Санкт-Петербург",
                phone = "+7 (901) 132-49-95",
                workingHours = "10:00 – 18:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Ника",
                address = "Косая линия, 19, Санкт-Петербург",
                phone = "+7 (911) 021-62-02",
                workingHours = "10:00 – 18:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Каррера",
                address = "Рощинская ул., 20В, Санкт-Петербург",
                phone = "+7 (901) 132-63-99",
                workingHours = "10:00 – 20:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "LikeАвто",
                address = "Прилукская ул., 20Б, Санкт-Петербург",
                phone = "+7 (812) 996-46-86",
                workingHours = "11:00 – 19:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Автоэлектрик 178",
                address = "Лиговский просп., 153, Санкт-Петербург (5 уровень паркинга)",
                phone = "+7 (931) 777-99-88",
                workingHours = "10:00 – 21:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Нитраль Авто",
                address = "ул. Ворошилова, 2И, Санкт-Петербург",
                phone = "+7 (900) 622-76-69",
                workingHours = "09:00 – 21:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Гаража.net",
                address = "Уральская ул., 4В, Санкт-Петербург",
                phone = "+7 (901) 132-42-08",
                workingHours = "10:00 – 00:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Fix4car",
                address = "Софийская ул., 8, корп. 1, стр. 1, Санкт-Петербург",
                phone = "+7 (812) 627-67-05",
                workingHours = "10:00 – 21:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "Coolinauto",
                address = "Пражская ул., 8, корп. 1, Санкт-Петербург",
                phone = "+7 (981) 335-92-58",
                workingHours = "10:00 – 19:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "Бухарестская, Международная",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Эстакада",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (966) 014-22-66",
                workingHours = "10:00 – 20:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            ),
            Company(
                name = "Субарубалт",
                address = "Грузинская ул., 3, корп. 1, Санкт-Петербург",
                phone = "+7 (812) 340-00-44",
                workingHours = "09:00 – 21:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Red
            ),
            Company(
                name = "Стэл",
                address = "Новочеркасский просп., 1Д, Санкт-Петербург",
                phone = "+7 (911) 923-95-15",
                workingHours = "09:00 – 17:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Green
            ),
            Company(
                name = "МБА сервис",
                address = "наб. Обводного канала, 134-136-138, корп. 240, Санкт-Петербург",
                phone = "+7 (921) 568-37-06",
                workingHours = "09:00 – 17:00",
                services = "Автодиагностика",
                price = "",
                metroStation = "",
                metroColor = Color.Yellow
            )
        )
    ),
    Service(
        id = 8,
        title = "Замена масла",
        description = "Список компаний, предоставляющих услуги замены масла",
        imageResId = R.drawable.maslo,
        companies = listOf(
            Company(
                name = "Mtl Garage",
                address = "13-я Красноармейская ул., 8, Санкт-Петербург",
                phone = "+7 (812) 385-59-99",
                workingHours = "10:00–21:00",
                services = "Замена масла",
                price = "от 1000 руб.",
                metroStation = "Технологический институт-2",
                metroColor = Color.Blue
            ),
            Company(
                name = "ДжиМоторс",
                address = "Транспортный пер., 10М, Санкт-Петербург",
                phone = "+7 (812) 240-80-36",
                workingHours = "10:00–21:00 пн-пт; 11:00–20:00 сб-вс",
                services = "Замена масла",
                price = "от 1200 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Green
            ),
            Company(
                name = "Мотюль-Гараж",
                address = "ул. Седова, 11АБ, Санкт-Петербург",
                phone = "+7 (812) 240-93-16",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 1500 руб.",
                metroStation = "Елизаровская",
                metroColor = Color.Red
            ),
            Company(
                name = "Замена масла в АКПП Motul Team",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (812) 960-60-50",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 2000 руб.",
                metroStation = "Ладожская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Persona",
                address = "Курская ул., 28/32, Санкт-Петербург",
                phone = "+7 (911) 926-92-28",
                workingHours = "09:30–20:30",
                services = "Замена масла",
                price = "от 1800 руб.",
                metroStation = "Обводный канал",
                metroColor = Color.Green
            ),
            Company(
                name = "Авто 98",
                address = "Лиговский просп., 60-62АБ, Санкт-Петербург",
                phone = "+7 (812) 921-16-00",
                workingHours = "10:00–20:00 пн-пт; 10:00–19:00 сб",
                services = "Замена масла",
                price = "от 1300 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Green
            ),
            Company(
                name = "Дилижанс",
                address = "просп. Елизарова, 34В, Санкт-Петербург",
                phone = "+7 (812) 240-90-35",
                workingHours = "09:00–22:30 пн-сб; 09:00–20:30 вс",
                services = "Замена масла",
                price = "от 1600 руб.",
                metroStation = "Елизаровская",
                metroColor = Color.Blue
            ),
            Company(
                name = "AutoComplex78",
                address = "Лиговский просп., 60-62АБ, Санкт-Петербург",
                phone = "+7 (812) 214-43-13",
                workingHours = "10:00–21:00",
                services = "Замена масла",
                price = "от 1800 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Blue
            ),
            Company(
                name = "Мб Стар Мерседес",
                address = "Тележная ул., 34, Санкт-Петербург",
                phone = "+7 (921) 995-99-52",
                workingHours = "11:00–21:00 пн-сб",
                services = "Замена масла",
                price = "от 2000 руб.",
                metroStation = "Площадь Александра Невского-2",
                metroColor = Color.Red
            ),
            Company(
                name = "Audi-Rus",
                address = "Лиговский просп., 60-62М, Санкт-Петербург",
                phone = "+7 (812) 923-31-34",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 2100 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Blue
            ),
            Company(
                name = "Итала",
                address = "Лиговский просп., 60-62Н, Санкт-Петербург",
                phone = "+7 (812) 910-49-15",
                workingHours = "11:00–20:00 пн-сб",
                services = "Замена масла",
                price = "от 1800 руб.",
                metroStation = "Лиговский проспект",
                metroColor = Color.Green
            ),
            Company(
                name = "Мобил 1 Центр",
                address = "ул. Ворошилова, 6И, Санкт-Петербург",
                phone = "+7 (931) 009-29-19",
                workingHours = "09:00–20:00",
                services = "Замена масла",
                price = "от 2200 руб.",
                metroStation = "Проспект Большевиков",
                metroColor = Color.Red
            ),
            Company(
                name = "СТО Motul Сервис",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (969) 200-39-35",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 2500 руб.",
                metroStation = "Ладожская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Тойота-Сервис Лиговский",
                address = "Ново-Рыбинская ул., 19-21А, Санкт-Петербург",
                phone = "+7 (812) 237-30-03",
                workingHours = "10:00–21:00",
                services = "Замена масла",
                price = "от 3000 руб.",
                metroStation = "Фрунзенская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Алекссервис",
                address = "8-я Красноармейская ул., 20АБ, Санкт-Петербург",
                phone = "+7 (812) 251-05-38",
                workingHours = "10:00–00:00 пн-пт; 11:00–00:00 сб-вс",
                services = "Замена масла",
                price = "от 3200 руб.",
                metroStation = "Балтийская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "M50",
                address = "Миргородская ул., 30, Санкт-Петербург",
                phone = "+7 (812) 611-23-11",
                workingHours = "10:00–20:00",
                services = "Замена масла",
                price = "от 3400 руб.",
                metroStation = "Площадь Александра Невского-2",
                metroColor = Color.Green
            ),
            Company(
                name = "WinAuto-Express",
                address = "Лесной просп., 78, Санкт-Петербург",
                phone = "+7 (812) 425-38-40",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 3500 руб.",
                metroStation = "Лесная",
                metroColor = Color.Blue
            ),
            Company(
                name = "OilCity",
                address = "Афонская ул., 17, корп. 1, Санкт-Петербург",
                phone = "+7 (812) 995-61-35",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 2000 руб.",
                metroStation = "Удельная",
                metroColor = Color.Red
            ),
            Company(
                name = "Формула автосервиса",
                address = "ул. Маршала Новикова, 41, корп. 1, Санкт-Петербург",
                phone = "+7 (812) 643-43-48",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 2500 руб.",
                metroStation = "Комендантский проспект",
                metroColor = Color.Green
            ),
            Company(
                name = "АвтоТеорема",
                address = "просп. Тореза, 71В, Санкт-Петербург",
                phone = "+7 (812) 603-40-55",
                workingHours = "08:30–22:00",
                services = "Замена масла",
                price = "от 2700 руб.",
                metroStation = "Удельная",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Multimotors78",
                address = "Афонская ул., 1, корп. 2, Санкт-Петербург",
                phone = "+7 (911) 277-00-93",
                workingHours = "10:00–21:00 пн-пт; 11:00–20:00 сб-вс",
                services = "Замена масла",
                price = "от 2800 руб.",
                metroStation = "Удельная",
                metroColor = Color.Red
            ),
            Company(
                name = "Автогринвич",
                address = "Зеленогорская ул., 4, Санкт-Петербург",
                phone = "+7 (812) 649-00-00",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 3000 руб.",
                metroStation = "Пионерская",
                metroColor = Color.Green
            ),
            Company(
                name = "Форсаж",
                address = "Тихорецкий просп., 6, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 327-10-10",
                workingHours = "09:00–21:00",
                services = "Замена масла",
                price = "от 3200 руб.",
                metroStation = "Политехническая",
                metroColor = Color.Yellow
            )
        )
    ),
    Service(
        id = 9,
        title = "Ремонт АКПП",
        description = "Список компаний, осуществляющие Ремонт АКПП",
        imageResId = R.drawable.akpp,
        companies = listOf(
            Company(
                name = "Эстакада",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (966) 014-22-66",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Fix4car",
                address = "Софийская ул., 8к1Г11, Санкт-Петербург (этаж 2)",
                phone = "+7 (812) 666-14-44",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Международная",
                metroColor = Color.Green
            ),
            Company(
                name = "Купчино Трансмишн",
                address = "ул. Салова, 47, Санкт-Петербург (помещение 4H, 11Н)",
                phone = "+7 (812) 240-84-38, +7 (812) 676-50-96",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Ремонт АКПП CVT DSG DCT",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Red
            ),
            Company(
                name = "ProАКПП",
                address = "ул. Самойловой, 7, Санкт-Петербург (этаж 1)",
                phone = "+7 (921) 916-86-33",
                workingHours = "пн-пт 11:00–19:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Волковская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Дилижанс",
                address = "просп. Елизарова, 34В, Санкт-Петербург",
                phone = "+7 (812) 240-90-35",
                workingHours = "пн-сб 09:00–22:30; вс 09:00–20:30",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Елизаровская",
                metroColor = Color.Green
            ),
            Company(
                name = "АКПП центр",
                address = "Софийская ул., 4Д, Санкт-Петербург",
                phone = "+7 (901) 417-50-53",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Red
            ),
            Company(
                name = "К14",
                address = "ул. Профессора Качалова, 14В, Санкт-Петербург",
                phone = "+7 (812) 200-47-25, +7 (812) 449-41-14",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Площадь Александра Невского-2",
                metroColor = Color.Blue
            ),
            Company(
                name = "Орбита",
                address = "ул. Маршала Говорова, 29Л, Санкт-Петербург",
                phone = "+7 (812) 454-66-03, +7 (812) 454-04-06, +7 (812) 454-60-60",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Кировский завод",
                metroColor = Color.Green
            ),
            Company(
                name = "4WaysVag",
                address = "Политехническая ул., 9С, Санкт-Петербург",
                phone = "+7 (812) 565-01-55",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Площадь Мужества",
                metroColor = Color.Blue
            ),
            Company(
                name = "Автогринвич",
                address = "Зеленогорская ул., 4, Санкт-Петербург",
                phone = "8 (800) 234-01-49, +7 (812) 649-00-00, +7 (911) 920-48-48",
                workingHours = "ежедневно, 09:00–20:59",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Пионерская",
                metroColor = Color.Green
            ),
            Company(
                name = "Центр АТ",
                address = "ул. Есенина, 19/2, Санкт-Петербург (этаж 1)",
                phone = "+7 (901) 317-51-61, +7 (812) 292-36-22",
                workingHours = "пн-пт 09:00–19:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Проспект Просвещения",
                metroColor = Color.Green
            ),
            Company(
                name = "Akpp-dsg-service",
                address = "Торфяная дорога, 21Д, Санкт-Петербург",
                phone = "+7 (812) 240-76-77",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Старая Деревня",
                metroColor = Color.Red
            ),
            Company(
                name = "Ремонт Powershift",
                address = "Белоостровская ул., 9Б, Санкт-Петербург",
                phone = "+7 (812) 604-67-87",
                workingHours = "ежедневно, 09:00–22:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Лесная",
                metroColor = Color.Green
            ),
            Company(
                name = "Ремонт АКПП Пик",
                address = "Кушелевская дорога, 20, Санкт-Петербург",
                phone = "+7 (812) 933-95-87",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Площадь Мужества",
                metroColor = Color.Yellow
            ),
            Company(
                name = "АКПП-Нева",
                address = "Сабировская ул., 41, Санкт-Петербург",
                phone = "+7 (905) 279-20-19, +7 (812) 407-17-93",
                workingHours = "ежедневно, 10:00–22:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Чёрная речка",
                metroColor = Color.Blue
            ),
            Company(
                name = "DSG-сервис",
                address = "Заусадебная ул., 15, Санкт-Петербург",
                phone = "+7 (921) 935-63-94",
                workingHours = "пн-пт 10:00–21:00; сб,вс 10:00–20:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Старая Деревня",
                metroColor = Color.Green
            ),
            Company(
                name = "В-Эксперт Сервис",
                address = "Парашютная ул., 51, Санкт-Петербург (секция В1)",
                phone = "+7 (812) 303-85-85",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Ремонт АКПП",
                price = "",
                metroStation = "Комендантский проспект",
                metroColor = Color.Red
            )

        )
    ),
    Service(
        id = 10,
        title = "Замена катализаторов",
        description = "Список компаний, осуществляющие замену катализаторов",
        imageResId = R.drawable.kataliki,
        companies = listOf(
            Company(
                name = "Эстакада",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (966) 014-22-66",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Авто-Выхлоп",
                address = "ул. Руставели, 11А, Санкт-Петербург",
                phone = "+7 (812) 223-74-92",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Академическая",
                metroColor = Color.Green
            ),
            Company(
                name = "СПб-Глушитель",
                address = "Екатерининский просп., 5АГ5, Санкт-Петербург (этаж 1)",
                phone = "+7 (812) 997-25-65, +7 (981) 822-96-36, +7 (921) 997-25-65",
                workingHours = "пн-сб 10:00–18:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Удельная",
                metroColor = Color.Yellow
            ),
            Company(
                name = "СПб-Глушитель",
                address = "Светлановский просп., 3, корп. 3, Санкт-Петербург",
                phone = "+7 (984) 888-53-43",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Удельная (закрыта)",
                metroColor = Color.Red
            ),
            Company(
                name = "Мир глушителей",
                address = "просп. Маршала Блюхера, 1, корп. 1Е, Санкт-Петербург",
                phone = "+7 (812) 926-15-15, +7 (911) 926-15-15, +7 (911) 923-27-25, +7 (921) 913-06-37, +7 (981) 975-64-78",
                workingHours = "ежедневно, 09:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Лесная",
                metroColor = Color.Green
            ),
            Company(
                name = "Доктор Глушитель",
                address = "ул. Коммуны, 15, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 926-53-23",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Проспект Большевиков",
                metroColor = Color.Blue
            ),
            Company(
                name = "Студия-выхлопа",
                address = "ул. Савушкина, 89, Санкт-Петербург",
                phone = "+7 (812) 467-97-83, +7 (921) 954-19-80",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Старая Деревня",
                metroColor = Color.Red
            ),
            Company(
                name = "Системы выхлопа",
                address = "ул. Ванеева, 10, Санкт-Петербург",
                phone = "+7 (911) 924-33-36, +7 (901) 132-11-20",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Green
            ),
            Company(
                name = "Центр ремонта глушителей глушачок",
                address = "Якорная ул., 16, Санкт-Петербург",
                phone = "+7 (812) 922-77-08, +7 (911) 922-77-08, +7 (812) 988-77-08",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Blue
            ),
            Company(
                name = "ГлушакоФФ",
                address = "ул. Ворошилова, 2АЕ, Санкт-Петербург (этаж 2)",
                phone = "+7 (812) 988-77-91, +7 (952) 288-77-91",
                workingHours = "пн-пт 11:00–20:00; сб,вс 11:00–19:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Мастер Глушителей",
                address = "Кантемировская ул., 39Д, Санкт-Петербург",
                phone = "+7 (812) 946-60-02, +7 (812) 928-13-43",
                workingHours = "ежедневно, 10:00–19:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Лесная",
                metroColor = Color.Red
            ),
            Company(
                name = "Стайер",
                address = "просп. Космонавтов, 55, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 240-47-47, +7 (812) 240-47-20",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Звёздная",
                metroColor = Color.Green
            ),
            Company(
                name = "Ремзона",
                address = "Софийская ул., 63С, Санкт-Петербург",
                phone = "+7 (812) 240-47-68",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Проспект Славы",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Чип Профи",
                address = "Заставская ул., 11, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 608-64-45, +7 (929) 156-59-16",
                workingHours = "пн-пт 09:00–18:00; сб 10:00–17:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Московские ворота",
                metroColor = Color.Red
            ),
            Company(
                name = "Глушитель Box",
                address = "Софийская ул., 8, корп. 1, стр. 1, Санкт-Петербург (1б2)",
                phone = "+7 (921) 900-25-48, +7 (812) 900-25-48, +7 (921) 918-35-65",
                workingHours = "пн-сб 09:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Green
            ),
            Company(
                name = "Катавто",
                address = "Витебская Сортировочная ул., 2, корп. 1, Санкт-Петербург",
                phone = "++7 (900) 641-16-33",
                workingHours = "пн-пт 09:00–19:00; сб 09:00–17:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Волковская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "ZetAuto",
                address = "ул. Салова, 70, корп. 4, Санкт-Петербург (Метро: Бухарестская, этаж 1)",
                phone = "+7 (812) 214-33-83",
                workingHours = "ежедневно, 09:00–20:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Red
            ),
            Company(
                name = "Ns Service",
                address = "Бухарестская ул., 6А, Санкт-Петербург (общий вход с табличкой 'NS Service – ремонт Ниссан и Инфинити', этаж 1)",
                phone = "+7 (812) 445-80-53",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Волковская",
                metroColor = Color.Green
            ),
            Company(
                name = "СервисПлюс",
                address = "Ташкентская ул., 3Б, Санкт-Петербург",
                phone = "+7 (812) 245-69-69, +7 (921) 937-37-38",
                workingHours = "пн-сб 10:30–21:00",
                services = "Замена катализаторов",
                price = "",
                metroStation = "Московские ворота",
                metroColor = Color.Yellow
            )
        )
    ),
    Service(
        id = 11,
        title = "Автоэлектрик",
        description = "Список компаний, осуществляющие работу по электрике авто",
        imageResId = R.drawable.avtoelektrik,
        companies = listOf(
            Company(
                name = "Автоэлектрик диагностика",
                address = "ул. Композиторов, 4, Санкт-Петербург (помещение 176Н)",
                phone = "+7 (906) 257-55-81",
                workingHours = "ежедневно, 09:00–22:00",
                services = "Автоэлектрик диагностика",
                price = "",
                metroStation = "Озерки",
                metroColor = Color.Green
            ),
            Company(
                name = "Автоэлектрик",
                address = "Новороссийская ул., 53Б, Санкт-Петербург (Синий ангар)",
                phone = "+7 (911) 145-70-57",
                workingHours = "ежедневно, 10:00–22:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Площадь Мужества",
                metroColor = Color.Red
            ),
            Company(
                name = "Vitauto",
                address = "ул. Шкапина, 32-34, Санкт-Петербург (Метро: Балтийская)",
                phone = "+7 (911) 927-05-07",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Балтийская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Территория автосервиса",
                address = "просп. Испытателей, 1, корп. 3, Санкт-Петербург",
                phone = "+7 (812) 643-21-33, +7 (921) 581-31-15",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Пионерская",
                metroColor = Color.Green
            ),
            Company(
                name = "Созвездие",
                address = "Лиговский просп., 246Б, Санкт-Петербург",
                phone = "+7 (911) 920-67-20, +7 (911) 777-00-95",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Московские ворота",
                metroColor = Color.Red
            ),
            Company(
                name = "Автоэлектрик",
                address = "24-я линия Васильевского острова, 25, корп. 2, Санкт-Петербург",
                phone = "+7 (953) 171-00-12 (доб. +79811343704), +7 (953) 171-00-12, +7 (981) 134-37-04",
                workingHours = "пн-пт 10:00–20:00; сб,вс 11:00–18:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Василеостровская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Автоэлектрик 178",
                address = "Лиговский просп., 153, Санкт-Петербург (5 уровень паркинга)",
                phone = "+7 (931) 777-99-88",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Обводный канал",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Инсервис",
                address = "Полюстровский просп., 59, Санкт-Петербург (Заезд с Полюстровского)",
                phone = "+7 (921) 941-40-11, +7 (911) 925-24-57",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Выборгская",
                metroColor = Color.Green
            ),
            Company(
                name = "AvtoLabSpb",
                address = "ул. Профессора Качалова, 9Г, Санкт-Петербург",
                phone = "+7 (812) 994-62-20",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "Площадь Александра Невского-2",
                metroColor = Color.Red
            ),
            Company(
                name = "ODINservice",
                address = "Малый просп. Васильевского острова, 62, корп. 1, Санкт-Петербург",
                phone = "+7 (921) 905-12-49 (viber, whatsapp)",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автоэлектрик",
                price = "",
                metroStation = "",
                metroColor = Color.Blue
            )
        )
    ),
    Service(
        id = 12,
        title = "Ремонт тормозов",
        description = "Список компаний, осуществляющие ремонт тормозов",
        imageResId = R.drawable.remonttormoz,
        companies = listOf(
            Company(
                name = "Питер Моторс",
                address = "Кожевенная линия, 34, Санкт-Петербург",
                phone = "+7 (999) 247-13-14, +7 (999) 247-02-12",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Автосервис",
                price = "",
                metroStation = "Приморская",
                metroColor = Color.Blue
            ),
            Company(
                name = "WinAuto-Express",
                address = "Лесной просп., 78, Санкт-Петербург (этаж 1)",
                phone = "+7 (812) 425-38-40",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Лесная",
                metroColor = Color.Green
            ),
            Company(
                name = "КСАвтоСервис",
                address = "Рижский просп., 37, лит.З, Санкт-Петербург",
                phone = "+7 (812) 251-58-62, +7 (812) 984-01-65, +7 (962) 684-01-65",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Нарвская",
                metroColor = Color.Red
            ),
            Company(
                name = "Суппорт 7",
                address = "Бассейная ул., 38Е, Санкт-Петербург",
                phone = "+7 (921) 906-07-00, +7 (901) 372-04-24",
                workingHours = "пн-пт 10:30–19:00; сб 10:30–18:00; вс 11:00–17:00",
                services = "Автосервис",
                price = "",
                metroStation = "Парк Победы",
                metroColor = Color.Green
            ),
            Company(
                name = "Volk Brake Performance",
                address = "Лиговский просп., 256В, Санкт-Петербург (этаж 1)",
                phone = "+7 (981) 335-59-06",
                workingHours = "пн-пт 11:00–20:00; сб,вс 11:00–18:00",
                services = "Автосервис",
                price = "",
                metroStation = "Московские ворота",
                metroColor = Color.Red
            ),
            Company(
                name = "Joker Auto",
                address = "Молодёжный пер., 2, Кировский район, Санкт-Петербург (ул. Ивана Черных, 31)",
                phone = "+7 (812) 777-18-86",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Нарвская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Максималь",
                address = "Уральская ул., 19А, Санкт-Петербург (1)",
                phone = "+7 (911) 926-80-77, +7 (999) 537-69-20, +7 (911) 081-11-79, +7 (911) 267-65-00",
                workingHours = "пн-сб 11:00–20:00",
                services = "Автосервис",
                price = "",
                metroStation = "Приморская",
                metroColor = Color.Green
            ),
            Company(
                name = "Алексавто",
                address = "наб. реки Волковки, 7Д, Санкт-Петербург",
                phone = "+7 (904) 510-22-89, +7 (921) 186-17-23",
                workingHours = "вт-сб 11:00–19:00",
                services = "Автосервис",
                price = "",
                metroStation = "Волковская",
                metroColor = Color.Blue
            ),
            Company(
                name = "Франсавто",
                address = "Ириновский просп., 14, корп. 2, Санкт-Петербург",
                phone = "+7 (812) 986-30-09",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Ладожская",
                metroColor = Color.Yellow
            ),
            Company(
                name = "Америкар",
                address = "Софийская ул., 2, Санкт-Петербург",
                phone = "+7 (812) 317-22-05",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Green
            ),
            Company(
                name = "Алекссервис",
                address = "8-я Красноармейская ул., 20АБ, Санкт-Петербург (этаж 1)",
                phone = "+7 (812) 251-05-38, +7 (904) 602-39-94",
                workingHours = "пн-пт 10:00–00:00; сб,вс 11:00–00:00",
                services = "Автосервис",
                price = "",
                metroStation = "Балтийская",
                metroColor = Color.Red
            ),
            Company(
                name = "Автомобильное бюро Карпов",
                address = "Кронверкская ул., 21, Санкт-Петербург",
                phone = "8 (800) 550-65-20",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Петроградская",
                metroColor = Color.Green
            ),
            Company(
                name = "Ремзона № 1",
                address = "Уральская ул., 19А, Санкт-Петербург (Ворота 9,10)",
                phone = "+7 (812) 645-67-45",
                workingHours = "пн-пт 10:00–20:00; сб 11:00–17:00",
                services = "Автосервис",
                price = "",
                metroStation = "Приморская",
                metroColor = Color.Red
            ),
            Company(
                name = "Гудворк",
                address = "Яхтенная ул., 19, корп. 2, Санкт-Петербург",
                phone = "+7 (901) 132-35-54",
                workingHours = "ежедневно, 10:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Беговая",
                metroColor = Color.Blue
            ),
            Company(
                name = "Ремзона № 1",
                address = "Уральская ул., 19А, Санкт-Петербург (Ворота 9,10)",
                phone = "+7 (812) 645-67-45",
                workingHours = "пн-пт 10:00–20:00; сб 11:00–17:00",
                services = "Автосервис",
                price = "",
                metroStation = "Приморская",
                metroColor = Color.Green
            ),
            Company(
                name = "Авто Логово",
                address = "Коломяжский просп., 15, корп. 8, Санкт-Петербург",
                phone = "+7 (929) 178-01-11",
                workingHours = "ежедневно, 09:30–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Пионерская",
                metroColor = Color.Red
            ),
            Company(
                name = "Автосервис № 1",
                address = "ул. Шкапина, 46, Санкт-Петербург",
                phone = "+7 (981) 805-50-01",
                workingHours = "ежедневно, 10:00–20:00",
                services = "Автосервис",
                price = "",
                metroStation = "Балтийская",
                metroColor = Color.Green
            ),
            Company(
                name = "Формула ремонта",
                address = "ул. Салова, 57, корп. 1, Санкт-Петербург (Салова д.57, к.1)",
                phone = "+7 (812) 467-92-28",
                workingHours = "ежедневно, 09:00–21:00",
                services = "Автосервис",
                price = "",
                metroStation = "Бухарестская",
                metroColor = Color.Yellow
            )
        )
    ),
)


val CustomPurple = Color(0xFF800080)
// после нажатия services, открывается окно с горзионтальными колонками,
// где при нажатии открывается details fragment with maps
// скидки - гор. столбики


// при входе в приложение сторис об использование приложения
// платные дороги - узнать информацию, оплата штрафов - сделать шаблона перед запуском с оплатой тарифа для нас
// дизайн блока приборной панели (разделить по блокам для информативности)
// услуги подготовить шаблон (шапку) для сотрудничества других компаний (подготовка презентации приложения майкар)
// помощь на дороге - отдельные вкладки


// мой авто - наполняемость (добавка фото авто личного) ...>
// профиль - редакция и сохрание (добавка сообществ)