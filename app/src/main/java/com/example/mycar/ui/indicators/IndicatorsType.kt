package com.example.mycar.ui.indicators

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycar.R

data class CategoryItem(val name: String, val iconRes: Int)

private val indicatorCategories = listOf(
            CategoryItem(
                "Неисправность тормозной системы (если индикатор включается во время движения). Рекомендация: долив тормозной жидкости при снижении ее уровня не решит проблему, ехать на машине нельзя. Помогут только вызов эвакуатора и доставка автомобиля на СТО.",
                R.drawable.helpersf
            ),
            CategoryItem(
                "Низкое давление масла в двигателе. Рекомендация: безопасно остановитесь и заглушите двигатель. Проверьте уровень по руководству. Даже нормальный уровень не подтверждает нормальное давление; не продолжайте поездку с горящим предупреждением. Организуйте диагностику и эвакуацию.",
                R.drawable.helperss
            ),
            CategoryItem("Перегрев двигателя. Рекомендация: безопасно остановитесь и заглушите двигатель. Не открывайте горячую систему охлаждения. После остывания проверьте уровень по инструкции; при течи или повторном предупреждении организуйте помощь.", R.drawable.helperst),
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
                "Отсутствует зарядка аккумулятора. Рекомендация: безопасно остановитесь и обратитесь за помощью. Возможны неисправности системы зарядки или привода агрегатов. Не регулируйте ремни у работающего двигателя и не рассчитывайте на гарантированный запас хода от батареи.",
                R.drawable.helpersseven
            ), // и грунтовки
            CategoryItem(
                "Неисправность подушки безопасности. Рекомендация: немедленно отправляться на СТО.",
                R.drawable.helperseight
            ),
            CategoryItem("Напоминание о ремне безопасности. Рекомендация: перед поездкой убедитесь, что все пассажиры пристёгнуты. Если сигнал остаётся при застёгнутых ремнях, проверьте предметы на сиденьях и обратитесь для диагностики; не отключайте датчики и не используйте заглушки.", R.drawable.helpersnine),
            CategoryItem("Открыта дверь. Рекомендация: остановитесь безопасно и убедитесь, что двери закрыты и зафиксированы. Если сообщение остаётся, не считайте его автоматически ошибкой датчика: проверьте замки в сервисе.", R.drawable.ind1),
            CategoryItem("Открыт капот. Рекомендация: до движения убедитесь, что капот полностью закрыт и зафиксирован. Если предупреждение остаётся, нужна проверка замка и датчика; не продолжайте поездку с незафиксированным капотом.", R.drawable.ind3),
            CategoryItem("Открыт багажник. Рекомендация: безопасно остановитесь, закрепите груз и закройте крышку. При сохраняющемся предупреждении проверьте замок и датчик в сервисе.", R.drawable.ind55),//error
            CategoryItem(
                "Неисправность батареи автомобиля с гибридной или электрической силовой установкой. Рекомендация: остановиться и вызвать эвакуатор для доставки автомобиля на СТО.",
                R.drawable.ind4
            ),
            CategoryItem("Разряжена батарейка ключа. Рекомендация: замените элемент по инструкции. Способ аварийного доступа и запуска зависит от модели; механический ключ не всегда позволяет запустить двигатель.", R.drawable.ind56),//error
            CategoryItem(
                "Предупреждение о серьезной проблеме. Рекомендация: нажать соответствующую кнопку для ознакомления с содержанием предупреждения, которое отобразится на дисплее приборного щитка, и принять решение о возможности дальнейшего движения.\n" +
                        "\n", R.drawable.ind6
            ),
            CategoryItem("Индикатор неисправности двигателя. Рекомендация: при мигании, сильной тряске или потере мощности остановитесь и обратитесь за помощью. При постоянном свечении прочитайте сообщение автомобиля и согласуйте диагностику; не стирайте ошибки до проверки.", R.drawable.ind7),
            CategoryItem("Предупреждение о воздушном фильтре. Рекомендация: сверьте значение значка с руководством. На заглушенном двигателе проверьте подходящий фильтр в сервисе; одноразовый бумажный элемент не мойте и не эксплуатируйте двигатель без него.", R.drawable.ind8),
            CategoryItem("Не закрыт топливный бак.", R.drawable.ind9),
            CategoryItem(
                "Наличие воды в топливной системе дизельного двигателя.\n",
                R.drawable.ind10
            ),
            CategoryItem("Низкий уровень топлива. Рекомендация: запланируйте ближайшую безопасную заправку, не полагаясь на точность остаточного запаса хода. Если после заправки сигнал остаётся, нужна диагностика показаний уровня.", R.drawable.ind11),
            CategoryItem("Перегрев или неисправность катализатора", R.drawable.ind12),
            CategoryItem("Неисправность сажевого фильтра", R.drawable.ind13),
            CategoryItem(
                "Перегрев или сбой в работе автоматической коробки передач. Рекомендация: остановиться и дать АКП остыть, а затем обратиться на СТО.",
                R.drawable.ind14
            ),
            CategoryItem("Износ тормозных колодок.", R.drawable.ind15),
            CategoryItem("Неисправность ABS. Рекомендация: антиблокировочная функция может быть недоступна; при изменении торможения или одновременном красном сигнале тормозов прекратите поездку. Проверьте руководство и организуйте диагностику.", R.drawable.ind16),
            CategoryItem("Сбой в работе системы стабилизации.", R.drawable.ind17),
            CategoryItem(
                "Сбой в работе электромеханического стояночного тормоза.\n",
                R.drawable.ind18
            ),
            CategoryItem("Предупреждение давления в шинах. Рекомендация: остановитесь безопасно и измерьте давление на холодных колёсах по табличке автомобиля. Осмотрите шины на повреждения. Не списывайте сигнал на датчик и не сбрасывайте TPMS до проверки давления.", R.drawable.ind19),
            CategoryItem("Электронный ключ не обнаружен. Рекомендация: проверьте наличие ключа и батарейку. Штатную точку распознавания и резервный способ запуска найдите в руководстве вашей модели.", R.drawable.ind20),
            CategoryItem("Низкий уровень омывающей жидкости. Рекомендация: долейте подходящую по сезону жидкость. Если уровень достаточен, но сигнал остаётся, требуется проверка датчика и системы.", R.drawable.ind21),
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

@Composable
fun NavigationComponentt(onBack: () -> Unit = {}) {
    var selected by rememberSaveable { mutableStateOf(-1) }
    var query by rememberSaveable { mutableStateOf("") }
    val accent = Color(0xFFFFA666)
    val muted = Color(0xFFA2A8B0)
    BackHandler(enabled = selected >= 0) { selected = -1 }
    MaterialTheme(colors = darkColors(primary = accent, surface = Color(0xFF1B1E22), background = Color(0xFF101214))) {
        Column(Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (selected >= 0) selected = -1 else onBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад", tint = accent)
                }
                Text(if (selected >= 0) "Значение индикатора" else "Приборная панель", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            if (selected in indicatorCategories.indices) {
                val category = indicatorCategories[selected]
                LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    item {
                        Image(painterResource(category.iconRes), null, contentScale = ContentScale.Fit,
                            modifier = Modifier.size(110.dp).clip(RoundedCornerShape(20.dp)).background(Color.White).padding(14.dp))
                    }
                    item { Text(category.name.substringBefore("Рекомендация:").trim(), color = Color.White,
                        fontSize = 22.sp, lineHeight = 30.sp, fontWeight = FontWeight.SemiBold) }
                    if ("Рекомендация:" in category.name) item {
                        Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(20.dp)) {
                            Column(Modifier.padding(18.dp)) {
                                Text("Что делать", color = accent, fontWeight = FontWeight.SemiBold)
                                Text(category.name.substringAfter("Рекомендация:").trim(), color = Color.White,
                                    fontSize = 15.sp, lineHeight = 24.sp, modifier = Modifier.padding(top = 10.dp))
                            }
                        }
                    }
                    item { Text("Сверьте символ и сообщение с руководством вашей модели. Одинаковые значки могут иметь разные значения; цвет и режим мигания тоже важны.",
                        color = muted, fontSize = 13.sp, lineHeight = 21.sp) }
                }
            } else {
                Text("Что показывает автомобиль?", color = Color.White, fontSize = 24.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
                Text("Выберите похожий значок. Уточните его значение по руководству вашей модели.", color = muted,
                    fontSize = 13.sp, lineHeight = 20.sp, modifier = Modifier.padding(horizontal = 20.dp))
                OutlinedTextField(query, { query = it }, singleLine = true, placeholder = { Text("Масло, тормоза, свет…") },
                    leadingIcon = { Icon(Icons.Default.Search, null) }, shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(20.dp))
                val filtered = indicatorCategories.withIndex().filter { it.value.name.contains(query.trim(), ignoreCase = true) }
                if (filtered.isEmpty()) Text("Ничего не найдено. Попробуйте другое название.", color = muted, modifier = Modifier.padding(20.dp))
                LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(filtered, key = { _, item -> item.index }) { _, item ->
                        Column(Modifier.clip(RoundedCornerShape(18.dp)).background(MaterialTheme.colors.surface)
                            .clickable { selected = item.index }.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painterResource(item.value.iconRes), null, contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(12.dp)).background(Color.White).padding(8.dp))
                            Text(item.value.name.substringBefore('.').substringBefore("Рекомендация:"), color = Color.White,
                                fontSize = 11.sp, lineHeight = 15.sp, maxLines = 3, overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth().heightIn(min = 53.dp).padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

