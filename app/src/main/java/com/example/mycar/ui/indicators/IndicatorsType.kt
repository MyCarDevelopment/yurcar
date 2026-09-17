package com.example.mycar.ui.indicators

import androidx.compose.foundation.layout.Column

import androidx.compose.ui.unit.dp
import com.example.mycar.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun CatalogScreenn(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {




        // Список категорий с иконками
        val categories = listOf(
            CategoryItem(
                "Неисправность тормозной системы (если индикатор включается во время движения). Рекомендация: долив тормозной жидкости при снижении ее уровня не решит проблему, ехать на машине нельзя. Помогут только вызов эвакуатора и доставка автомобиля на СТО.",
                R.drawable.helpersf
            ),
            CategoryItem(
                "Низкое давление масла в двигателе. Рекомендация: срочно проверить уровень масла и при необходимости долить, чтобы добраться до СТО. Если уровень в норме, возможно, неисправен масляный насос или забит масляный фильтр. Тогда только буксировка или вызов эвакуатора для доставки автомобиля на СТО.",
                R.drawable.helperss
            ),
            CategoryItem(
                "Перегрев двигателя. Рекомендация: немедленно остановиться, выключить двигатель и дождаться, когда он остынет. После этого проверить уровень охлаждающей жидкости и при необходимости долить ее. Если уровень в норме, возможен целый букет неисправностей в системе охлаждения. Тогда только буксировка или вызов эвакуатора для доставки автомобиля на СТО.",
                R.drawable.helperst
            ),
            CategoryItem(
                "Неисправность автоматической коробки передач. Рекомендация: Только вызов эвакуатора для доставки автомобиля на СТО.\n",
                R.drawable.helpersff
            ),
            CategoryItem(
                "Неисправность усилителя руля. Рекомендация: буксировка или вызов эвакуатора для доставки автомобиля на СТО.",
                R.drawable.helpersfi
            ),
            CategoryItem(
                "Ошибка блокировки руля. Рекомендация: только буксировка или вызов эвакуатора для доставки автомобиля на СТО.\n",
                R.drawable.helperssi
            ),
            CategoryItem(
                "Отсутствует зарядка аккумулятора. Рекомендация: Подтянуть ремень генератора. Если это не помогло, своим ходом, используя имеющийся заряд аккумулятора, можно протянуть лишь несколько километров при условии отключения по максимуму энергопотребителей. СТО далеко? Если да, тогда только буксировка автомобиля или вызов эвакуатора.",
                R.drawable.helpersseven
            ), // и грунтовки
            CategoryItem(
                "Неисправность подушки безопасности. Рекомендация: немедленно отправляться на СТО.",
                R.drawable.helperseight
            ),
            CategoryItem(
                "Непристегнутый ремень безопасности. Если все пассажиры пристегнуты, и на сиденьях нет тяжелых предметов, причина в электрике. Двигаться можно, но необходим ремонт.\n",
                R.drawable.helpersnine
            ),
            CategoryItem(
                "Открыты двери. Рекомендация: если двери закрыты, причина в электрике. Двигаться можно, но необходим ремонт.",
                R.drawable.ind1
            ),
            CategoryItem(
                "Открыт капот. Рекомендация: если капот закрыт, причина в электрике. Двигаться можно, но необходим ремонт.",
                R.drawable.ind3
            ),
            CategoryItem(
                "Открыт багажник. Рекомендация: если багажник или грузовой отсек закрыты, причина в электрике. Двигаться можно, но необходим ремонт.",
                R.drawable.ind55
            ),//error
            CategoryItem(
                "Неисправность батареи автомобиля с гибридной или электрической силовой установкой. Рекомендация: остановиться и вызвать эвакуатор для доставки автомобиля на СТО.",
                R.drawable.ind4
            ),
            CategoryItem(
                "Батарейка электронного ключа зажигания разряжена. Рекомендация: если нет возможности сразу заменить батарейку, для закрывания или открывания дверей и запуска или отключения двигателя воспользуйтесь интегрированным в брелок металлическим ключом.",
                R.drawable.ind56
            ),//error
            CategoryItem(
                "Предупреждение о серьезной проблеме. Рекомендация: нажать соответствующую кнопку для ознакомления с содержанием предупреждения, которое отобразится на дисплее приборного щитка, и принять решение о возможности дальнейшего движения.\n" +
                        "\n", R.drawable.ind6
            ),
            CategoryItem("Сбой в работе двигателя.", R.drawable.ind7),
            CategoryItem(
                "Засорен воздушный фильтр. Рекомендация: при заглушенном двигателе снять и прочистить (при необходимости заменить) фильтр",
                R.drawable.ind8
            ),
            CategoryItem("Не закрыт топливный бак.", R.drawable.ind9),
            CategoryItem(
                "Наличие воды в топливной системе дизельного двигателя.\n",
                R.drawable.ind10
            ),
            CategoryItem(
                "Низкий уровень топлива. Рекомендация: как можно быстрее заправить автомобиль. Если индикатор продолжает гореть, причина в электрике. Двигаться можно, но необходим ремонт.",
                R.drawable.ind11
            ),
            CategoryItem("Перегрев или неисправность катализатора", R.drawable.ind12),
            CategoryItem("Неисправность сажевого фильтра", R.drawable.ind13),
            CategoryItem(
                "Перегрев или сбой в работе автоматической коробки передач. Рекомендация: остановиться и дать АКП остыть, а затем обратиться на СТО.",
                R.drawable.ind14
            ),
            CategoryItem("Износ тормозных колодок.", R.drawable.ind15),
            CategoryItem("Сбой в работе ABS.", R.drawable.ind16),
            CategoryItem("Сбой в работе системы стабилизации.", R.drawable.ind17),
            CategoryItem(
                "Сбой в работе электромеханического стояночного тормоза.\n",
                R.drawable.ind18
            ),
            CategoryItem(
                "Падение давления в шинах. Рекомендация: с помощью проверенного манометра убедитесь в факте падения давления. Случается, измеряющие его датчики дают неверную информацию. В таком случае после перезапуска двигателя индикатор, как правило, гаснет.\n",
                R.drawable.ind19
            ),
            CategoryItem(
                "Электронный ключ зажигания не обнаружен в автомобиле. Рекомендация: при наличии ключа поднести его ближе к передней панели и перезапустить двигатель. После этого индикатор, как правило, гаснет.",
                R.drawable.ind20
            ),
            CategoryItem(
                "Низкий уровень омывающей жидкости. Рекомендация: долить жидкость. Если ее уровень в норме, причина в электрике. Необходим ремонт.",
                R.drawable.ind21
            ),
            CategoryItem("Неисправность корректора фар.", R.drawable.ind22),
            CategoryItem("Перегорела лампочка в фаре.", R.drawable.ind23),
            CategoryItem("Система старт-стоп включена.", R.drawable.ind24),
            CategoryItem(
                "Напоминание о необходимости нажать педаль тормоза перед запуском двигателя на автомобилях с автоматической коробкой передач.",
                R.drawable.ind25
            ),
            CategoryItem(
                "Индикация работы свеч накаливания в дизельных двигателях. Этот значок должен гаснуть через несколько секунд после включения зажигания, а затем возможен запуск непрогретого двигателя. Если же пиктограмма продолжает гореть, это признак неисправности свечей накала.",
                R.drawable.ind26
            ),
            CategoryItem("Включен режим «ЕСО» (экономии топлива).", R.drawable.ind27),
            CategoryItem(
                "Движение в режиме электротяги на автомобиле с гибридной силовой установкой.",
                R.drawable.ind28
            ),
            CategoryItem(
                "Включен режим ограничения мощности тягового электромотора у электромобиля. Рекомендация: если после зарядки батареи индикатор продолжает гореть, обратиться на СТО.",
                R.drawable.ind29
            ),
            CategoryItem(
                "Напоминание о необходимости переключить передачу механической коробки вверх.\n",
                R.drawable.ind30
            ),
            CategoryItem("Включен круиз-контроль", R.drawable.ind31),
            CategoryItem("Включен адаптивный круиз-контроль", R.drawable.ind32),
            CategoryItem(
                "Оповещение об опасном сближении с преградой по ходу движения при включенном адаптивном круиз-контроле.\n",
                R.drawable.ind33
            ),
            CategoryItem(
                "Активирован режим автоматического удержания автомобиля с АКП на месте при остановке.",
                R.drawable.ind34
            ),
            CategoryItem("Низкая температура, возможен гололед.", R.drawable.ind35),
            CategoryItem("Включилась система стабилизации.", R.drawable.ind36),
            CategoryItem("Активирован режим контроля рядности", R.drawable.ind37),
            CategoryItem("Активна система контроля слепых зон", R.drawable.ind38),
            CategoryItem("Активирован парктроник.", R.drawable.ind39),
            CategoryItem("Активирован режим полного привода.", R.drawable.ind40),
            CategoryItem("Включен понижающий ряд трансмиссии.", R.drawable.ind41),
            CategoryItem(
                "Активирована блокировка заднего межколесного дифференциала.",
                R.drawable.ind42
            ),
            CategoryItem("Активирован ассистент спуска с горы.", R.drawable.ind43),
            CategoryItem("Активирован внедорожный круиз-контроль", R.drawable.ind44),
            CategoryItem("Активирован режим движения с прицепом.", R.drawable.ind45),
            CategoryItem("Включены габаритные огни.", R.drawable.ind46),
            CategoryItem("Включен адаптивный режим фар.", R.drawable.ind47),
            CategoryItem("Включен ближний свет фар.", R.drawable.ind48),
            CategoryItem("Включен дальний свет фар.", R.drawable.ind49),
            CategoryItem("Включены передние противотуманные фары", R.drawable.ind50),
            CategoryItem("Включены задние противотуманные фары", R.drawable.ind51),
            CategoryItem(
                "Активирован иммобилайзер. В этом случае индикатор работает в прерывистом режиме. Если же пиктограмма горит постоянно, это признак неисправности системы.",
                R.drawable.ind52
            ),
            CategoryItem("Напоминание водителю о необходимости отдыха.", R.drawable.ind53),
            CategoryItem("Напоминание о необходимости проведения ТО.", R.drawable.ind54)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.size) { index ->
                CategoryCard(category = categories[index], onClick = {
                    navController.navigate("category/${categories[index].name}")
                })
            }
        }


    }
}

data class CategoryItem(val name: String, val iconRes: Int)

@Composable
fun CategoryCard(category: CategoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Кадрирование изображения, чтобы оно заполнило карточку
            )
            Text(
                text = category.name,
                fontSize = 0.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp) // Отступ для текста внутри карточки
                    .background(Color(0x80000000)) // Полупрозрачный фон для читаемости текста
            )
        }
    }
}

@Composable
fun CategoryDetailScreen(navController: NavController, category: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = category, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Возврат на HomeFragment
                        navController.popBackStack(route = "catalog", inclusive = false)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24), // Замените на свою иконку
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = " $category",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            // Дополнительный контент можно добавить здесь
        }
    }
}



// Навигация между экранами
@Composable
fun NavigationComponentt() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "catalog") {
        composable("catalog") {
            CatalogScreenn(navController = navController)
        }
        composable("category/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            CategoryDetailScreen(
                navController = navController,
                category = category ?: "Неизвестная категория"
            )
        }
    }
}

