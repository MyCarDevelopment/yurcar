package com.example.mycar.ui.crash

import androidx.fragment.app.Fragment
import com.example.mycar.R
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mycar.BaseActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.*
class CrashFragment : Fragment() {

override fun onCreateView(
    inflater: android.view.LayoutInflater,
    container: android.view.ViewGroup?,
    savedInstanceState: Bundle?
): View {
    return (requireActivity() as BaseActivity).createComposeView {
        InfoScreen()
    }
}
}

@Composable
fun InfoScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "🚗 Помощь водителю",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = "ДТП, эвакуация, европротокол и полезная информация для водителей Санкт-Петербурга.",
                color = Color.Gray
            )
        }

        item {
            HelpCard(
                title = "🚘 Эвакуация автомобиля",
                color = Color(0xFF1565C0)
            ) {

                SectionTitle("Что делать если машину эвакуировали?")

                Bullet("Уточните местонахождение автомобиля")
                Bullet("Узнайте адрес штрафстоянки")
                Bullet("Подготовьте документы")
                Bullet("Получите разрешение на выдачу автомобиля")

                Spacer(Modifier.height(12.dp))

                SectionTitle("Необходимые документы")

                Bullet("Паспорт")
                Bullet("Водительское удостоверение")
                Bullet("СТС")
                Bullet("Полис ОСАГО")

                Spacer(Modifier.height(12.dp))

                SectionTitle("Комитет по транспорту")

                PhoneItem(
                    "Комитет по транспорту",
                    "Исполкомская ул., 16",
                    "+7 (812) 111-11-11"
                )

                Spacer(Modifier.height(12.dp))

                SectionTitle("Штрафстоянки")

                ParkingCard(
                    "Штрафстоянка №1",
                    "ул. Северная, 12",
                    "+7 (812) 222-22-22"
                )

                ParkingCard(
                    "Штрафстоянка №2",
                    "пр. Московский, 48",
                    "+7 (812) 333-33-33"
                )

                ParkingCard(
                    "Штрафстоянка №3",
                    "ул. Автомобильная, 5",
                    "+7 (812) 444-44-44"
                )
            }
        }

        item {

            HelpCard(
                title = "🚨 ДТП",
                color = Color(0xFFD32F2F)
            ) {

                SectionTitle("Первые действия")

                Bullet("Остановить автомобиль")
                Bullet("Включить аварийную сигнализацию")
                Bullet("Выставить знак аварийной остановки")
                Bullet("Проверить наличие пострадавших")
                Bullet("Зафиксировать повреждения")

                Spacer(Modifier.height(12.dp))

                SectionTitle("Особые случаи ДТП")

                ExpandableItem(
                    title = "🚋 ДТП на трамвайных путях",
                    description = """
        • Не покидать место до фиксации
        • Сделать фото положения авто и рельсов
        • По возможности освободить путь
        • Вызвать ГИБДД при спорной ситуации
    """.trimIndent()
                )

                ExpandableItem(
                    title = "🅿 ДТП на парковке",
                    description = """
        • Зафиксировать оба автомобиля
        • Проверить камеры (если есть)
        • Оставить записку/контакт при отсутствии владельца
        • Можно оформить европротокол
    """.trimIndent()
                )

                ExpandableItem(
                    title = "🏠 ДТП во дворе",
                    description = """
        • Считается полноценным ДТП
        • Вызов ГИБДД при споре
        • Фиксация машин со всех сторон
        • Запись свидетелей
    """.trimIndent()
                )

                ExpandableItem(
                    title = "🚗 Каршеринг ДТП",
                    description = """
        • Сразу уведомить сервис каршеринга
        • Не покидать место ДТП
        • Фотографии со всех ракурсов
        • Дождаться инструкций оператора
    """.trimIndent()
                )

                ExpandableItem(
                    title = "🚴 ДТП с велосипедистом",
                    description = """
        • Проверить состояние пострадавшего
        • Вызвать 112 при травмах
        • Зафиксировать место происшествия
        • Оформление через ГИБДД
    """.trimIndent()
                )

                ExpandableItem(
                    title = "🛴 ДТП с самокатом",
                    description = """
        • Часто приравнивается к ДТП с пешеходом
        • Проверить травмы
        • Вызвать экстренные службы при необходимости
        • Сделать фото дорожной обстановки
    """.trimIndent()
                )

                Spacer(Modifier.height(12.dp))

                SectionTitle("Если есть пострадавшие")

                Text(
                    text = "Немедленно звоните 112. Не перемещайте пострадавших без необходимости.",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {

            HelpCard(
                title = "📝 Европротокол",
                color = Color(0xFF2E7D32)
            ) {

                SectionTitle("Когда можно оформить")

                Bullet("Нет пострадавших")
                Bullet("Участвуют два автомобиля")
                Bullet("Есть действующий ОСАГО")
                Bullet("Нет серьёзных разногласий")

                Spacer(Modifier.height(12.dp))

                SectionTitle("Как заполнить")

                Bullet("Указать место и дату ДТП")
                Bullet("Заполнить данные участников")
                Bullet("Указать данные автомобилей")
                Bullet("Нарисовать схему ДТП")
                Bullet("Поставить подписи")

                Spacer(Modifier.height(12.dp))

                SectionTitle("После оформления")

                Bullet("Сделать фотографии")
                Bullet("Сохранить копию документов")
                Bullet("Передать документы страховщику")
                Bullet("Контролировать выплату")
            }
        }

        item {

            HelpCard(
                title = "☎ Полезные телефоны",
                color = Color(0xFFFF9800)
            ) {

                PhoneItem(
                    "Экстренные службы",
                    "112",
                    "Круглосуточно"
                )

                PhoneItem(
                    "Полиция",
                    "102",
                    "Круглосуточно"
                )

                PhoneItem(
                    "Скорая помощь",
                    "103",
                    "Круглосуточно"
                )

                PhoneItem(
                    "Эвакуация автомобилей",
                    "+7 (812) 555-55-55",
                    "Круглосуточно"
                )
            }
        }

        item {

            HelpCard(
                title = "⚠ Частые ошибки",
                color = Color(0xFF7B1FA2)
            ) {

                Bullet("Уезжают с места ДТП")
                Bullet("Не фотографируют повреждения")
                Bullet("Не фиксируют дорожную обстановку")
                Bullet("Не записывают свидетелей")
                Bullet("Подписывают документы не читая")
                Bullet("Не уведомляют страховую")
                Bullet("Неправильно рисуют схему ДТП")
            }
        }

        item {
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun InfoCard(
    icon: @Composable () -> Unit,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon()

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}


@Composable
fun HelpCard(
    title: String,
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = color,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            content()
        }
    }
}
@Composable
fun SectionTitle(text: String) {

    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(6.dp))
}
@Composable
fun Bullet(text: String) {

    Text(
        text = "• $text",
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
@Composable
fun PhoneItem(
    title: String,
    phone: String,
    info: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                title,
                fontWeight = FontWeight.Bold
            )

            Text(phone)

            Text(
                info,
                color = Color.Gray
            )
        }
    }
}
@Composable
fun ParkingCard(
    name: String,
    address: String,
    phone: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                name,
                fontWeight = FontWeight.Bold
            )

            Text(address)

            Text(
                phone,
                color = Color(0xFF1565C0)
            )
        }
    }
}
@Composable
fun ExpandableItem(
    title: String,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = if (expanded) "▲" else "▼",
                    color = Color.Gray
                )
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))

                Text(
                    text = description,
                    color = Color.DarkGray
                )
            }
        }
    }
}