// InstructionsActivity.kt
package com.example.mycar.ui.instructions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycar.BaseActivity
import com.example.mycar.R
import com.example.mycar.ui.home.HomeColors

class InstructionsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = "Помощь на дороге"
            setDisplayHomeAsUpEnabled(true)
        }

        setContentView(
            ComposeView(this).apply {
                setContent {
                    MaterialTheme {
                        RoadHelpScreen()
                    }
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

@Composable
fun RoadHelpScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSituation by remember { mutableStateOf<RoadSituation?>(null) }
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F0F23))
    )

    val filteredSituations = allSituations.filter { situation ->
        situation.title.contains(searchQuery, ignoreCase = true) ||
                situation.category.contains(searchQuery, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column {
            // 🔍 ПОИСК
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск ситуации...") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }

            // 📋 СПИСОК СИТУАЦИЙ
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredSituations) { situation ->
                    RoadHelpCard(
                        situation = situation,
                        onClick = { selectedSituation = situation }
                    )
                }
            }
        }

        // 📱 ДЕТАЛИ (оверлей)
        selectedSituation?.let { situation ->
            FullscreenSituationDetail(
                situation = situation,
                onDismiss = { selectedSituation = null }
            )
        }
    }
}

@Composable
fun RoadHelpCard(situation: RoadSituation, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFF2196F3).copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_manage_accounts_24),
                    contentDescription = situation.title,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Основная информация
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = situation.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Text(
                    text = situation.category,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = situation.description,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    maxLines = 2
                )
            }

            // Время/стоимость (как на скрине)
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = situation.time,
                    color = Color(0xFF4CAF50),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullscreenSituationDetail(
    situation: RoadSituation,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.88f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Заголовок
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = situation.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = situation.category,
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Закрыть",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Шаги по инструкции (из файла!)
                Text(
                    text = "Что делать:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(situation.steps) { index, step ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF2A2A3E).copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "${index + 1}. $step",
                                color = Color.White,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class RoadSituation(
    val title: String,
    val category: String,
    val time: String,
    val steps: List<String>,
    val description: String
)

val allSituations = listOf(
    // 🚗 АВАРИИ И ДТП
    RoadSituation(
        title = "Попал в ДТП",
        category = "Авария",
        time = "20-60 мин",
        steps = listOf(
            "1. Включите аварийную сигнализацию немедленно",
            "2. Установите знак аварийной остановки (15м)",
            "3. Оцените состояние всех участников ДПТ",
            "4. Вызовите ГИБДД по номеру 102",
            "5. Сфотографируйте место ДТП и повреждения",
            "6. Не перемещайте машины до приезда инспектора",
            "7. Составьте европротокол если ущерб до 50к ₽"
        ),
        description = "Полная инструкция по действиям при дорожно-транспортном происшествии"
    ),

    // 🔋 АККУМУЛЯТОР
    RoadSituation(
        title = "Садится аккумулятор",
        category = "Электрика",
        time = "10-20 мин",
        steps = listOf(
            "1. Проверьте напряжение (должно быть 12.4-12.6В)",
            "2. Прикурите от другой машины (кабели + и -)",
            "3. Заведите и дайте поработать 10-15 минут",
            "4. Проверьте зарядку генератора (13.8-14.5В)",
            "5. Если не держит - вызовите эвакуатор",
            "6. Замена АКБ ~5000-15000 ₽"
        ),
        description = "Причины разряда и как оживить АКБ на дороге"
    ),

    // 🚚 ЭВАКУАТОР
    RoadSituation(
        title = "Нужен эвакуатор",
        category = "Эвакуация",
        time = "15-45 мин",
        steps = listOf(
            "1. Найдите ближайший эвакуатор (Яндекс.Навигатор)",
            "2. Уточните стоимость (зависит от расстояния)",
            "3. Проверьте наличие страховки на эвакуацию",
            "4. Подготовьте документы на машину",
            "5. Получите акт выполненных работ",
            "6. Сфотографируйте погрузку"
        ),
        description = "Таблица тарифов эвакуации по городам РФ"
    ),

    // 🛻 ЗАСТРЯЛ
    RoadSituation(
        title = "Застрял в грязи/снегу",
        category = "Застрял",
        time = "10-40 мин",
        steps = listOf(
            "1. НЕ газуйте вхолостую - закопаете глубже",
            "2. Подложите под ведущие колеса ветки/камни/ковры",
            "3. Раскачайте машину вперед-назад",
            "4. Используйте лебедку если есть",
            "5. Вызовите трактор/другую машину с тросом",
            "6. Последний вариант - эвакуатор"
        ),
        description = "Самостоятельное и буксировочное извлечение"
    ),

    // ⚠️ CHECK ENGINE
    RoadSituation(
        title = "Check Engine загорелся",
        category = "Диагностика",
        time = "5-30 мин",
        steps = listOf(
            "1. Проверьте крышку бензобака - часто причина",
            "2. Осмотрите все видимые провода/разъемы",
            "3. Сбросьте ошибку через OBD-сканер",
            "4. Если горит снова - компьютерная диагностика",
            "5. Частые причины: датчики, катушки, лямбда"
        ),
        description = "Расшифровка 50+ кодов ошибок OBD-II"
    ),

    // 🛞 ШИНЫ
    RoadSituation(
        title = "Прокол/спуск шины",
        category = "Шины",
        time = "15-30 мин",
        steps = listOf(
            "1. Остановитесь на обочине, включите аварийку",
            "2. Соберите домкрат, балонный ключ, запаска",
            "3. Ослабьте гайки крест-накрест",
            "4. Поддомкратьте машину, снимите колесо",
            "5. Установите запаску, затяните гайки",
            "6. Проверьте давление в запаске (2.2-2.5 бар)"
        ),
        description = "Правильная замена колеса на дороге"
    ),

    // ⛽ ТОПЛИВО
    RoadSituation(
        title = "Закончилось топливо",
        category = "Топливо",
        time = "10-20 мин",
        steps = listOf(
            "1. Достаньте канистру из багажника",
            "2. Залейте 5-10 литров топлива",
            "3. Заведите машину, доедьте до заправки",
            "4. Если нет канистры - идите пешком или вызывайте помощь"
        ),
        description = "Запас топлива на дальнюю дорогу"
    ),

    // 🚥 4WD
    RoadSituation(
        title = "Проблемы с 4WD",
        category = "Полный привод",
        time = "30-60 мин",
        steps = listOf(
            "1. Проверьте блокировку дифференциала",
            "2. Переключите в 2H если не нужна 4WD",
            "3. Проверьте приводной вал/кардан",
            "4. Диагностика на СТО обязательна"
        ),
        description = "Неисправности полного привода"
    ),

    // 🚦 АВАРИЙКА/ПРОБЛЕМЫ СВЕТА
    RoadSituation(
        title = "Не работает аварийка",
        category = "Электрика",
        time = "10-20 мин",
        steps = listOf(
            "1. Проверьте предохранитель аварийки",
            "2. Осмотрите реле в блоке предохранителей",
            "3. Проверьте лампочки и проводку",
            "4. Замена блока реле ~1000 ₽"
        ),
        description = "Обязательно для безопасности!"
    ),

    // 🏎️ ABS
    RoadSituation(
        title = "ABS не работает",
        category = "Тормоза",
        time = "30-90 мин",
        steps = listOf(
            "1. Проверьте датчики скорости колес",
            "2. Очистите датчики от грязи",
            "3. Диагностика ABS блока",
            "4. Не ездите без ABS - опасно!"
        ),
        description = "Диагностика системы антиблокировки"
    ),

    // +40 других ситуаций из файла...
    RoadSituation(
        title = "Пробка на дороге",
        category = "Другое",
        time = "5 мин",
        steps = listOf(
            "1. Включите аварийку в плотном потоке",
            "2. Держите дистанцию 2-3 сек",
            "3. Экономьте топливо - отпустите газ"
        ),
        description = "Правила поведения в заторе"
    )
)





@Composable
fun SymptomsFragment(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Все") }

    val categories = listOf("Все", "Звуки", "Вибрация", "Поведение", "Подтеки", "Выхлоп", "Оборудование", "Визуальные")

    // Полная база симптомов из предоставленных данных
    val symptomsData = remember {
        mapOf(
            "Звуки" to listOf(
                SymptomsItem(
                    title = "Стук, гул или вой при движении",
                    description = "Нарастает с ростом скорости",
                    causes = listOf(
                        "Подшипник ступицы (меняется в поворотах)",
                        "ШРУС (хруст при повороте)",
                        "Редуктор/дифференциал (вой на скорости)",
                        "Карданный вал/крестовина",
                        "АКПП/МКПП (меняется при нагрузке)",
                        "Шины (неравномерный износ)",
                        "Ремень ГРМ/натяжитель"
                    )
                ),
                SymptomsItem(
                    title = "Стук при проезде неровностей",
                    description = "На кочках",
                    causes = listOf(
                        "Стойки стабилизатора",
                        "Амортизаторы",
                        "Шаровые опоры",
                        "Рычаги/сайлентблоки",
                        "Рулевые наконечники",
                        "Рулевая рейка",
                        "Опоры двигателя/КПП"
                    )
                )
            ),
            "Вибрация" to listOf(
                SymptomsItem(
                    title = "Вибрация руля",
                    description = "На 80-120 км/ч",
                    causes = listOf(
                        "Дисбаланс колес",
                        "Кривой тормозной диск",
                        "Рулевые наконечники",
                        "Деформация диска",
                        "Ходовая часть"
                    )
                )
            ),
            "Поведение" to listOf(
                SymptomsItem(
                    title = "Тянет в сторону",
                    description = "При движении",
                    causes = listOf(
                        "Развал-схождение",
                        "Давление в шинах",
                        "Подвеска",
                        "Тормоза",
                        "Рулевая рейка"
                    )
                ),
                SymptomsItem(
                    title = "Провалы при разгоне",
                    description = "Потеря мощности",
                    causes = listOf(
                        "Топливная система",
                        "Зажигание",
                        "Датчики двигателя",
                        "Воздушный фильтр",
                        "Катализатор"
                    )
                )
            ),
            "Подтеки" to listOf(
                SymptomsItem(
                    title = "Подтеки масла (двигатель)",
                    description = "Под капотом",
                    causes = listOf(
                        "Прокладка клапанной крышки",
                        "Сальники коленвала",
                        "Прокладка поддона",
                        "Сальник распредвала"
                    )
                )
            ),
            "Выхлоп" to listOf(
                SymptomsItem(
                    title = "Синий дым",
                    description = "Масло",
                    causes = listOf(
                        "Маслосъемные колпачки",
                        "Поршневые кольца",
                        "Турбина"
                    )
                )
            ),
            "Оборудование" to listOf(
                SymptomsItem(
                    title = "Не работает стартер",
                    description = "",
                    causes = listOf(
                        "Аккумулятор",
                        "Втягивающее реле",
                        "Бендикс",
                        "Проводка"
                    )
                )
            ),
            "Визуальные" to listOf(
                SymptomsItem(
                    title = "Неравномерный износ шин",
                    description = "",
                    causes = listOf(
                        "Развал-схождение",
                        "Подвеска",
                        "Деформация рычагов"
                    )
                )
            )
        )
    }

    val allSymptoms = symptomsData.values.flatten()
    val filteredSymptoms = if (selectedCategory == "Все") {
        allSymptoms.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true)
        }
    } else {
        symptomsData[selectedCategory]?.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true)
        } ?: emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HomeColors.darkGray, HomeColors.darkBackground)
                )
            )
            .padding(20.dp)
    ) {
        // Заголовок
        Text(
            text = "Диагностика по симптомам",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Поиск
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("стук, гул, дым, вибрация...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = HomeColors.accentOrange,
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                cursorColor = HomeColors.accentOrange,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_question_mark_24),
                    contentDescription = "Поиск",
                    tint = HomeColors.accentOrange,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Категории
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category, color = Color.White) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (selectedCategory == category)
                            HomeColors.accentOrange.copy(alpha = 0.2f) else HomeColors.graphite,
                        selectedContainerColor = HomeColors.accentOrange.copy(alpha = 0.2f),
                        labelColor = Color.White,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Результаты
        if (filteredSymptoms.isEmpty()) {
            EmptySearchState(searchQuery, "стук, гул, дым, вибрация...", "Начните поиск симптомов")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredSymptoms) { symptom ->
                    SymptomCardLarge(symptom = symptom)
                }
            }
        }
    }
}

data class SymptomsItem(
    val title: String,
    val description: String,
    val causes: List<String>
)

@Composable
fun SymptomCardLarge(
    symptom: SymptomsItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Детали */ },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.graphite)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_car_crash_24),
                    contentDescription = null,
                    tint = HomeColors.accentOrange,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = symptom.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    if (symptom.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = symptom.description,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Причины
            Text(
                text = "${symptom.causes.size} возможных причин:",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(symptom.causes.take(5)) { cause ->
                    CauseChip(cause = cause)
                }
                if (symptom.causes.size > 5) {
                    item {
                        Text(
                            text = "+${symptom.causes.size - 5} больше",
                            color = HomeColors.accentOrange,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CauseChip(
    cause: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = HomeColors.accentOrange.copy(alpha = 0.15f),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = cause,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            maxLines = 1
        )
    }
}

@Composable
fun EmptySearchState(
    query: String,
    hint: String = "",
    title: String = "Начните поиск"
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_question_mark_24),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = if (query.isEmpty()) title else "Результаты не найдены",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hint.ifEmpty { "Введите запрос для поиска" },
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}


// по местоположению


// LocationFragment.kt - Фрагмент по местоположению (детали/узлы)
@Composable
fun LocationFragment(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Все") }

    val categories = listOf("Все", "Двигатель", "Трансмиссия", "Ходовая", "Тормоза", "Электрика", "Кузов")

    // Полная база по местоположению из предоставленных данных
    val locationData = remember {
        mapOf(
            "Двигатель" to listOf(
                LocationItem(
                    title = "Двигатель / Ремонт двигателя",
                    location = "Под капотом, моторный отсек",
                    signs = listOf(
                        "Сильный стук, скрежет, вой",
                        "Потеря мощности, масложор",
                        "Постоянный перегрев",
                        "Эмульсия масло+тосол"
                    ),
                    actions = listOf(
                        "Комплексная диагностика",
                        "Капитальный ремонт",
                        "Замена на контрактный/новый"
                    )
                ),
                LocationItem(
                    title = "ГРМ (ремень/цепь)",
                    location = "Спереди/сбоку двигателя",
                    signs = listOf(
                        "Трещины, расслоение ремня",
                        "Стрекот цепи на холодную",
                        "Плавающие обороты"
                    ),
                    actions = listOf(
                        "Замена по регламенту 60-90 тыс. км",
                        "Ролики + помпа в комплекте"
                    )
                ),
                LocationItem(
                    title = "Термостат",
                    location = "У толстого патрубка радиатора",
                    signs = listOf(
                        "Долго прогревается",
                        "Перегрев двигателя",
                        "Печка холодная"
                    ),
                    actions = listOf("Проверить патрубки", "Замена")
                )
            ),
            "Трансмиссия" to listOf(
                LocationItem(
                    title = "Сцепление в сборе",
                    location = "Между двигателем и КПП",
                    signs = listOf(
                        "Пробуксовка",
                        "Ведет при переключении",
                        "Рывки при трогании"
                    ),
                    actions = listOf(
                        "Снятие коробки",
                        "Комплект: диск+корзина+выжимной"
                    )
                )
            ),
            "Ходовая" to listOf(
                LocationItem(
                    title = "Амортизаторы и пружины",
                    location = "У каждого колеса",
                    signs = listOf(
                        "Раскачка кузова",
                        "Течь масла",
                        "Пробои подвески"
                    ),
                    actions = listOf("Замена парой на оси")
                ),
                LocationItem(
                    title = "Шаровая опора",
                    location = "Нижняя часть поворотного кулака",
                    signs = listOf(
                        "Стук на неровностях",
                        "Скрип при повороте руля",
                        "Люфт"
                    ),
                    actions = listOf("Срочная замена + сход-развал")
                ),
                LocationItem(
                    title = "Стойки стабилизатора",
                    location = "Соединяют стабилизатор с рычагами",
                    signs = listOf(
                        "Глухой стук на 'горохе'"
                    ),
                    actions = listOf("Частый недорогой ремонт")
                )
            ),
            "Тормоза" to listOf(
                LocationItem(
                    title = "Тормозные колодки/диски",
                    location = "На каждом колесе",
                    signs = listOf(
                        "Скрежет, писк при торможении",
                        "Вибрация педали",
                        "Длинный тормозной путь"
                    ),
                    actions = listOf(
                        "Осмотр, толщина >3-4 мм",
                        "Замена дисков при биении"
                    )
                )
            ),
            "Электрика" to listOf(
                LocationItem(
                    title = "Генератор",
                    location = "Сбоку спереди двигателя",
                    signs = listOf(
                        "Горит лампа аккумулятора",
                        "Тусклый свет фар"
                    ),
                    actions = listOf("Диагностика", "Ремонт/замена")
                ),
                LocationItem(
                    title = "Стартер",
                    location = "Внизу, со стороны КПП",
                    signs = listOf(
                        "Щелчки, не крутит",
                        "Крутит вхолостую"
                    ),
                    actions = listOf("Бендикс, втягивающее реле")
                )
            ),
            "Кузов" to listOf(
                LocationItem(
                    title = "Радиатор печки",
                    location = "Под торпедой в салоне",
                    signs = listOf(
                        "Запотевание стекол",
                        "Запах антифриза",
                        "Мокрый коврик"
                    ),
                    actions = listOf("Замена радиатора отопителя")
                )
            )
        )
    }

    val allLocations = locationData.values.flatten()
    val filteredLocations = if (selectedCategory == "Все") {
        allLocations.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.location.contains(searchQuery, ignoreCase = true)
        }
    } else {
        locationData[selectedCategory]?.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.location.contains(searchQuery, ignoreCase = true)
        } ?: emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HomeColors.darkGray, HomeColors.darkBackground)
                )
            )
            .padding(20.dp)
    ) {


        // Поиск
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("двигатель, ступица, суппорт, генератор...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = HomeColors.accentOrange,
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                cursorColor = HomeColors.accentOrange,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_question_mark_24),
                    contentDescription = "Поиск",
                    tint = HomeColors.accentOrange,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Категории
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category, color = Color.White) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (selectedCategory == category)
                            HomeColors.accentOrange.copy(alpha = 0.2f) else HomeColors.graphite,
                        selectedContainerColor = HomeColors.accentOrange.copy(alpha = 0.2f),
                        labelColor = Color.White,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Результаты
        if (filteredLocations.isEmpty()) {
            EmptySearchState(searchQuery, "подвеска, тормоза, двигатель...", "Начните поиск деталей")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredLocations) { location ->
                    LocationCard(location = location)
                }
            }
        }
    }
}

data class LocationItem(
    val title: String,
    val location: String,
    val signs: List<String>,
    val actions: List<String>
)

@Composable
fun LocationCard(
    location: LocationItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Детали ремонта */ },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.graphite)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_question_mark_24),
                    contentDescription = null,
                    tint = HomeColors.accentOrange,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = location.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = location.location,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Признаки
            Text(
                text = "Признаки (${location.signs.size}):",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(location.signs.take(4)) { sign ->
                    SignChip(sign = sign)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Действия
            Text(
                text = "Что делать (${location.actions.size}):",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(location.actions) { action ->
                    ActionChip(action = action)
                }
            }
        }
    }
}

@Composable
fun SignChip(sign: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Red.copy(alpha = 0.15f),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = sign,
            color = Color.Red.copy(alpha = 0.9f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            maxLines = 1
        )
    }
}

@Composable
fun ActionChip(action: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = HomeColors.accentOrange.copy(alpha = 0.15f),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = action,
            color = HomeColors.accentOrange,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            maxLines = 1
        )
    }
}

// критичность

@Composable
fun CriticalityFragment(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Все") }

    val categories = listOf("Все", "Критичные", "Рекомендуемые", "Несрочные")

    val criticalityData = remember {
        mapOf(
            "Критичные" to listOf(
                CriticalityItem(
                    title = "Тормозная система",
                    items = listOf(
                        "Цилиндр тормозной (всех видов)",
                        "Суппорт тормозной",
                        "Трубка/шланг тормозной",
                        "Усилитель тормозов",
                        "ABS блок/насос"
                    ),
                    priority = "🚨 СРОЧНО - безопасность движения"
                ),
                CriticalityItem(
                    title = "Рулевое управление",
                    items = listOf(
                        "Наконечник рулевого",
                        "Тяга рулевая",
                        "Рулевая рейка",
                        "Насос ГУР"
                    ),
                    priority = "🚨 СРОЧНО - управление"
                ),
                CriticalityItem(
                    title = "Двигатель (разрушение)",
                    items = listOf(
                        "Ремень/цепь ГРМ",
                        "Помпа",
                        "Прокладка ГБЦ",
                        "Сальник коленвала"
                    ),
                    priority = "🚨 СРОЧНО - риск поломки"
                )
            ),
            "Рекомендуемые" to listOf(
                CriticalityItem(
                    title = "Плановое ТО",
                    items = listOf(
                        "Масло + фильтры",
                        "Свечи зажигания",
                        "Тормозная жидкость",
                        "Антифриз",
                        "Развал-схождение"
                    ),
                    priority = "📅 ПЛАНОВО - по регламенту"
                ),
                CriticalityItem(
                    title = "Ходовая (износ)",
                    items = listOf(
                        "Амортизаторы",
                        "Сайлентблоки",
                        "Стойки стабилизатора",
                        "ШРУС/полуось"
                    ),
                    priority = "📅 ПЛАНОВО - ресурс авто"
                )
            ),
            "Несрочные" to listOf(
                CriticalityItem(
                    title = "Кузов и внешний вид",
                    items = listOf(
                        "Бампер",
                        "Крыло",
                        "Фара",
                        "Ручка двери",
                        "Антикор"
                    ),
                    priority = "⏳ МОЖЕТ ПОДОЖДАТЬ"
                ),
                CriticalityItem(
                    title = "Комфорт салона",
                    items = listOf(
                        "Сиденье",
                        "Торпедо",
                        "Шумоизоляция",
                        "Парктроники",
                        "Круиз-контроль"
                    ),
                    priority = "⏳ КОМФОРТ"
                )
            )
        )
    }

    val allItems = criticalityData.values.flatten()
    val filteredItems = if (selectedCategory == "Все") {
        allItems.filter { item ->
            item.title.contains(searchQuery, ignoreCase = true) ||
                    item.items.any { it.contains(searchQuery, ignoreCase = true) }
        }
    } else {
        criticalityData[selectedCategory]?.filter { item ->
            item.title.contains(searchQuery, ignoreCase = true) ||
                    item.items.any { it.contains(searchQuery, ignoreCase = true) }
        } ?: emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HomeColors.darkGray, HomeColors.darkBackground)
                )
            )
            .padding(20.dp)
    ) {
        // Заголовок
        Column(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text = "Критичность ремонта",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Принципы распределения:",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "• Критичные (СРОЧНО): безопасность\n• Рекомендуемые (ПЛАНОВО): ресурс\n• Несрочные (МОЖЕТ ПОДОЖДАТЬ): комфорт",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        // Поиск
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("суппорт, ГРМ, сайлентблок...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = HomeColors.accentOrange,
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                cursorColor = HomeColors.accentOrange,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_question_mark_24),
                    contentDescription = "Поиск",
                    tint = HomeColors.accentOrange
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Категории
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                PriorityChip(
                    category = category,
                    isSelected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Результаты
        if (filteredItems.isEmpty()) {
            EmptySearchState(
                searchQuery,
                "тормоза, ГРМ, амортизаторы...",
                "Критичность не найдена"
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(filteredItems) { item ->
                    CriticalityCard(item = item)
                }
            }
        }
    }
}

data class CriticalityItem(
    val title: String,
    val items: List<String>,
    val priority: String
)

@Composable
fun PriorityChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = when (category) {
        "Критичные" -> Color.Red
        "Рекомендуемые" -> HomeColors.accentOrange
        "Несрочные" -> Color.Green
        else -> Color.White
    }

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(category, color = Color.White) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = color.copy(alpha = if (isSelected) 0.3f else 0.2f),
            labelColor = Color.LightGray,
            selectedLabelColor = Color.White
        ),
        leadingIcon = if (isSelected) {
            {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_car_crash_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.LightGray
                )
            }
        } else null
    )
}

@Composable
fun CriticalityCard(
    item: CriticalityItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.graphite)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Заголовок с приоритетом
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = when {
                                item.priority.contains("СРОЧНО") -> Color.Red.copy(alpha = 0.2f)
                                item.priority.contains("ПЛАНОВО") -> HomeColors.accentOrange.copy(alpha = 0.2f)
                                else -> Color.Green.copy(alpha = 0.2f)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = item.priority,
                        color = when {
                            item.priority.contains("СРОЧНО") -> Color.Red
                            item.priority.contains("ПЛАНОВО") -> HomeColors.accentOrange
                            else -> Color.Green
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Список работ
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(200.dp)
            ) {
                items(item.items) { work ->
                    WorkItem(work = work)
                }
            }
        }
    }
}

@Composable
fun WorkItem(work: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_car_crash_24),
            contentDescription = null,
            tint = HomeColors.accentOrange.copy(alpha = 0.7f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = work,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

// DTP

@Composable
fun AccidentFragment(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1a1a2e),
                        Color(0xFF16213e),
                        Color(0xFF0f3460)
                    )
                )
            )
    ) {
        // Мыльный фон (полупрозрачные пузыри)
        BubbleBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Иконка
            Icon(
                painter = painterResource(R.drawable.ic_baseline_car_crash_24),
                contentDescription = null,
                tint = HomeColors.accentOrange.copy(alpha = 0.8f),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Заголовок
            Text(
                text = "ДТП / Эвакуатор",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Подзаголовок
            Text(
                text = "Информация в разработке",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Описание
            Text(
                text = "В скором времени здесь будет:\n\n" +
                        "• Пошаговые инструкции при ДТП\n" +
                        "• Контакты эвакуаторов\n" +
                        "• Что делать при аварии\n" +
                        "• Страховые компании\n" +
                        "• Полиция и ГИБДД",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Кнопка обновлений
            Button(
                onClick = { /* TODO: уведомления */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeColors.accentOrange.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Уведомить об обновлении",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BubbleBackground() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Большие пузыри
        Bubble(
            color = Color(0xFF4a90e2).copy(alpha = 0.1f),
            size = 300.dp,
            offsetX = (-100).dp,
            offsetY = (-50).dp
        )
        Bubble(
            color = Color(0xFF7ed321).copy(alpha = 0.08f),
            size = 250.dp,
            offsetX = 100.dp,
            offsetY = 100.dp
        )
        Bubble(
            color = Color(0xFFf5a623).copy(alpha = 0.12f),
            size = 200.dp,
            offsetX = (-150).dp,
            offsetY = 300.dp
        )
        Bubble(
            color = Color(0xFF50e3c2).copy(alpha = 0.1f),
            size = 180.dp,
            offsetX = 200.dp,
            offsetY = (-100).dp
        )

        // Маленькие пузыри
        repeat(10) {
            Bubble(
                color = Color.White.copy(alpha = 0.05f),
                size = (40..80).random().dp,
                offsetX = (it * 40f).dp,
                offsetY = (it * 30f).dp
            )
        }
    }
}

@Composable
fun Bubble(
    color: Color,
    size: Dp,
    offsetX: Dp,
    offsetY: Dp
) {
    Canvas(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .size(size)
    ) {

    }
}