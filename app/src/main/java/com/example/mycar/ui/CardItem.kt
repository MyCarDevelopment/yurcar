//package com.example.mycar.ui
//
//import android.content.Intent
//import android.net.Uri
//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.gestures.detectVerticalDragGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.ClickableText
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.CardDefaults
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import com.example.mycar.R
//import com.example.mycar.ui.home.HomeTheme
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.rememberPagerState
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//
//@Composable
//fun HomeScreenContent(
//    onNavigateToIndicators: () -> Unit,
//    onCardClick: (CardType) -> Unit,
//    onTelegramClick: () -> Unit,
//    onCooperationClick: () -> Unit
//) {
//    var selectedStoryIndex by remember { mutableStateOf(-1) }
//    var selectedServiceIndex by remember { mutableStateOf(-1) }
//
//    MaterialTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(HomeTheme.darkGray, HomeTheme.darkBackground)
//                    )
//                )
//        ) {
//            Column(
//                modifier = Modifier
//                    .verticalScroll(rememberScrollState())
//                    .fillMaxSize()
//                    .padding(horizontal = 12.dp, vertical = 8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                StoriesNews(onStorySelected = { selectedStoryIndex = it })
//                HomeBanner(onBannerClick = onNavigateToIndicators)
//                TopGridMenu(
//                    cards = topGridCards,
//                    backgrounds = cardBackgrounds,
//                    onCardClick = onCardClick
//                )
//                TelegramInvite(onClick = onTelegramClick)
//                ExtraGridMenu(
//                    cards = listOf(CardType.SERVICES, CardType.SIXTH),
//                    backgrounds = mapOf(
//                        CardType.SERVICES to R.drawable.avtoelektrik,
//                        CardType.SIXTH to R.drawable.maslo
//                    ),
//                    onCardClick = onCardClick
//                )
//                BigRectangleCard(
//                    cardType = CardType.SELL_CAR,
//                    backgroundRes = R.drawable.fines,
//                    onClick = { onCardClick(CardType.SELL_CAR) }
//                )
//                CooperationCard(onClick = onCooperationClick)
//            }
//
//            // OVERLAYS
//            selectedStoryIndex.takeIf { it >= 0 }?.let { index ->
//                StoryViewer(
//                    stories = demoStories,
//                    initialIndex = index,
//                    onDismiss = { selectedStoryIndex = -1 }
//                )
//            }
//        }
//    }
//}
//
//// 📰 СТОРИС
//@Composable
//fun StoriesNews(onStorySelected: (Int) -> Unit) {
//    val stories = listOf(
//        "🚗 Актуально" to Story("Story 1", R.drawable.news, R.drawable.st2),
//        "⚡ Быстро" to Story("Story 2", R.drawable.newsf, R.drawable.st3),
//        "🔥 Горячее" to Story("Story 3", R.drawable.newst, R.drawable.st4),
//        "💡 Советы" to Story("Story 1", R.drawable.newss, R.drawable.st5),
//        "🛠️ Сервис" to Story("Story 2", R.drawable.ind38, R.drawable.st1)
//    )
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp),
//        elevation = HomeTheme.cardElevation,
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(12.dp)
//        ) {
//            Text(
//                text = "📰 Новости",
//                color = Color.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                items(stories.size) { index ->
//                    val (title, story) = stories[index]
//                    StoryItemNews(
//                        title = title,
//                        previewImageRes = story.previewImageRes,
//                        isViewed = index < 2,
//                        onClick = { onStorySelected(index) }
//                    )
//                }
//            }
//        }
//    }
//}
//
//// 🔥 БАННЕР
//@Composable
//fun HomeBanner(onBannerClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .clickable { onBannerClick() },
//        shape = RoundedCornerShape(14.dp),
//        elevation = HomeTheme.cardElevation.copy(defaultElevation = 8.dp),
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = "🔥 Специальное предложение!",
//                    color = Color.White,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = "Нажмите, чтобы узнать подробности",
//                    color = HomeTheme.accentOrange,
//                    fontSize = 13.sp
//                )
//            }
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
//                contentDescription = "Подробнее",
//                tint = HomeTheme.accentOrange,
//                modifier = Modifier.size(28.dp)
//            )
//        }
//    }
//}
//
//// 📱 ГРИДЫ КАРТОЧЕК
//@Composable
//fun TopGridMenu(
//    cards: List<CardType>,
//    backgrounds: Map<CardType, Int>,
//    onCardClick: (CardType) -> Unit
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(15.dp),
//        elevation = HomeTheme.cardElevation,
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp)
//                .padding(HomeTheme.gridPadding),
//            contentPadding = PaddingValues(4.dp)
//        ) {
//            items(cards) { cardType ->
//                GridCardItem(
//                    cardType = cardType,
//                    backgroundRes = backgrounds[cardType] ?: R.drawable.fines,
//                    onClick = { onCardClick(cardType) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ExtraGridMenu(
//    cards: List<CardType>,
//    backgrounds: Map<CardType, Int>,
//    onCardClick: (CardType) -> Unit
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(15.dp),
//        elevation = HomeTheme.cardElevation,
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(107.dp)
//                .padding(HomeTheme.gridPadding),
//            contentPadding = PaddingValues(6.dp)
//        ) {
//            items(cards) { cardType ->
//                ExtraCardItem(
//                    cardType = cardType,
//                    backgroundRes = backgrounds[cardType]!!,
//                    onClick = { onCardClick(cardType) }
//                )
//            }
//        }
//    }
//}
//
//// 🃏 УНИВЕРСАЛЬНЫЕ КАРТОЧКИ
//@Composable
//fun GridCardItem(
//    cardType: CardType,
//    @DrawableRes backgroundRes: Int,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(4.dp)
//            .aspectRatio(1f)
//            .clickable { onClick() },
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(6.dp),
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        ImageCardContent(
//            backgroundRes = backgroundRes,
//            cardType = cardType,
//            textPosition = Alignment.BottomStart,
//            iconSize = 24.dp,
//            gradient = Brush.horizontalGradient(
//                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
//            )
//        )
//    }
//}
//
//@Composable
//fun ExtraCardItem(
//    cardType: CardType,
//    @DrawableRes backgroundRes: Int,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(6.dp)
//            .fillMaxSize()
//            .clickable { onClick() },
//        shape = RoundedCornerShape(14.dp),
//        elevation = CardDefaults.cardElevation(6.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
//    ) {
//        ImageCardContent(
//            backgroundRes = backgroundRes,
//            cardType = cardType,
//            textPosition = Alignment.BottomStart,
//            iconSize = 32.dp,
//            gradient = Brush.horizontalGradient(
//                colors = listOf(Color.Black.copy(alpha = 1.0f), Color.Black.copy(alpha = 0.0f))
//            )
//        )
//    }
//}
//
//@Composable
//fun BigRectangleCard(
//    cardType: CardType,
//    @DrawableRes backgroundRes: Int,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .clickable { onClick() },
//        shape = RoundedCornerShape(15.dp),
//        elevation = HomeTheme.cardElevation,
//        colors = CardDefaults.cardColors(containerColor = HomeTheme.graphite)
//    ) {
//        ImageCardContent(
//            backgroundRes = backgroundRes,
//            cardType = cardType,
//            textPosition = Alignment.TopStart,
//            iconSize = 36.dp,
//            isBigCard = true
//        )
//    }
//}
//
//// 🎯 ОСНОВНОЙ КОНТЕЙНЕР ДЛЯ КАРТОЧЕК С ИЗОБРАЖЕНИЕМ
//@Composable
//private fun ImageCardContent(
//    @DrawableRes backgroundRes: Int,
//    cardType: CardType,
//    textPosition: Alignment,
//    iconSize: Dp,
//    gradient: Brush,
//    isBigCard: Boolean = false
//) {
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Фон
//        Image(
//            painter = painterResource(id = backgroundRes),
//            contentDescription = cardType.title,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.matchParentSize()
//        )
//
//        // Градиент
//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .background(gradient)
//        )
//
//        // Контент
//        Column(
//            modifier = Modifier
//                .align(textPosition)
//                .padding(if (isBigCard) 16.dp else 12.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.Start
//        ) {
//            Icon(
//                painter = painterResource(id = cardType.icon),
//                contentDescription = cardType.title,
//                tint = Color.White,
//                modifier = Modifier.size(iconSize)
//            )
//            Spacer(modifier = Modifier.height(if (isBigCard) 8.dp else 6.dp))
//            Text(
//                text = cardType.title,
//                color = Color.White,
//                fontSize = if (isBigCard) 20.sp else 15.sp,
//                fontWeight = FontWeight.Bold,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun StoryViewer(
//    stories: List<Story>,
//    initialIndex: Int,
//    onDismiss: () -> Unit
//) {
//    val pagerState = rememberPagerState(initialPage = initialIndex)
//    var isPaused by remember { mutableStateOf(false) } // Пауза
//    var progress by remember { mutableStateOf(0f) } // Прогресс-бар
//    var verticalOffset by remember { mutableStateOf(0f) } // Смещение при смахивании
//    val coroutineScope = rememberCoroutineScope() // Для управления корутинами
//
//    LaunchedEffect(pagerState.currentPage, isPaused) {
//        progress = 0f
//        if (!isPaused) {
//            for (i in 1..100) {
//                progress = i / 100f
//                delay(50L)
//            }
//
//            // Переход на следующую сторис
//            if (pagerState.currentPage < stories.size - 1) {
//                pagerState.scrollToPage(pagerState.currentPage + 1)
//            } else {
//                onDismiss() // Закрыть, если это последняя сторис
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = {
//                        // Ставим на паузу при зажатии
//                        isPaused = true
//                        tryAwaitRelease() // Ждём завершения зажатия
//                        isPaused = false // Возобновляем прогресс
//                    },
//                    onTap = {
//                        // Переход на следующую сторис
//                        coroutineScope.launch {
//                            if (pagerState.currentPage < stories.size - 1) {
//                                pagerState.scrollToPage(pagerState.currentPage + 1)
//                            } else {
//                                onDismiss() // Закрыть, если это последняя сторис
//                            }
//                        }
//                    }
//                )
//            }
//            .pointerInput(Unit) {
//                detectVerticalDragGestures(
//                    onVerticalDrag = { _, dragAmount ->
//                        verticalOffset += dragAmount
//                    },
//                    onDragEnd = {
//                        if (verticalOffset > 300f || verticalOffset < -300f) {
//                            onDismiss()
//                        } else {
//                            verticalOffset = 0f
//                        }
//                    }
//                )
//            }
//            .offset { IntOffset(0, verticalOffset.toInt()) }
//    ) {
//        // Горизонтальное пролистывание сторис
//        HorizontalPager(
//            count = stories.size,
//            state = pagerState,
//            modifier = Modifier.fillMaxSize()
//        ) { page ->
//            val story = stories[page]
//            Image(
//                painter = painterResource(id = story.contentImageRes),
//                contentDescription = story.title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        // Прогресс-бар сверху
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(8.dp)
//                .align(Alignment.TopCenter)
//                .background(Color.Gray)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(progress)
//                    .background(Color.Cyan)
//            )
//        }
//
//        // Кнопка закрытия (крестик)
//        Icon(
//            imageVector = Icons.Default.Close,
//            contentDescription = "Close",
//            tint = Color.White,
//            modifier = Modifier
//                .size(32.dp)
//                .align(Alignment.TopEnd)
//                .padding(16.dp)
//                .clickable { onDismiss() }
//        )
//    }
//}
//
//
//@Composable
//fun TelegramInvite() {
//    val context = LocalContext.current
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(6.dp),
//        verticalAlignment = Alignment.CenterVertically // Выравнивание по вертикали
//    ) {
//        // Колонка с текстом
//        Column(
//            modifier = Modifier.weight(1f) // Заполняет оставшееся пространство
//        ) {
//            // Текст с приглашением
//            Text(
//                text = "Присоединяйтесь к нашему сообществу в Telegram!",
//                fontSize = 18.sp,
//                color = Color.White
//            )
//
//            Spacer(modifier = Modifier.height(4.dp)) // Отступ между текстом и ссылкой
//
//            // Кликабельная ссылка
//            Text(
//                text = "Вступить в сообщество",
//                color = Color(0xFF97CEF8), // Цвет ссылки (голубой)
//                fontSize = 16.sp,
//                textDecoration = TextDecoration.Underline,
//                modifier = Modifier.clickable {
//                    // Открываем ссылку в браузере
//                    val telegramUrl = "https://t.me/mycarcommunity"
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
//                    context.startActivity(intent)
//                }
//            )
//        }
//
//        // Кликабельная картинка Telegram справа
//        Image(
//            painter = painterResource(id = R.drawable.tg), // Замените на ваш ресурс
//            contentDescription = "Telegram Logo",
//            modifier = Modifier
//                .size(60.dp) // Размер изображения
//                .padding(start = 8.dp) // Отступ слева
//                .clickable {
//                    // Открываем ссылку в браузере
//                    val telegramUrl = "https://t.me/mycarcommunity"
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
//                    context.startActivity(intent)
//                }
//        )
//    }
//}
//@Composable
//fun DetailsScreenOverlay(service: Service, onDismiss: () -> Unit) {
//    val context = LocalContext.current
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF1B1C1C).copy(alpha = 0.9f)) // Полупрозрачный фон
//            .padding(16.dp)
//    ) {
//        // Контент страницы
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    color = Color(0xFF2B2B2B),
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(16.dp)
//        ) {
//            item {
//                Text(
//                    text = service.name,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//            items(service.companies) { company ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = 4.dp,
//                    backgroundColor = Color(0xFF3B3B3B)
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text(
//                            text = "Компания: ${company.name}",
//                            color = Color.White,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        ClickableText(
//                            text = AnnotatedString("Адрес: ${company.address}"),
//                            style = TextStyle(color = Color.Cyan, fontSize = 16.sp),
//                            onClick = {
//                                val intent = Intent(
//                                    Intent.ACTION_VIEW,
//                                    Uri.parse("https://yandex.ru/maps/?text=${Uri.encode(company.address)}")
//                                )
//                                context.startActivity(intent)
//                            }
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        ClickableText(
//                            text = AnnotatedString("Телефон: ${company.phone}"),
//                            style = TextStyle(color = Color.Cyan, fontSize = 16.sp),
//                            onClick = {
//                                val intent = Intent(
//                                    Intent.ACTION_DIAL,
//                                    Uri.parse("tel:${company.phone}")
//                                )
//                                context.startActivity(intent)
//                            }
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Услуги: ${company.services}",
//                            color = Color.Gray,
//                            fontSize = 16.sp
//                        )
//                    }
//                }
//            }
//        }
//
//        // Кнопка закрытия
//        IconButton(
//            onClick = onDismiss,
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(8.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Close,
//                contentDescription = "Закрыть",
//                tint = Color.White
//            )
//        }
//    }
//}
//
//private val demoStories = listOf(
//    Story("Story 1", R.drawable.ind38, R.drawable.st2),
//    Story("Story 2", R.drawable.ind38, R.drawable.st3),
//    Story("Story 3", R.drawable.ind38, R.drawable.st4),
//    Story("Story 4", R.drawable.ind38, R.drawable.st5),
//    Story("Story 5", R.drawable.ind38, R.drawable.st1)
//)
//@Composable
//fun CooperationCard(
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(65.dp) // чуть выше, чтобы влез и заголовок и описание
//            .clickable { onClick() },
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(6.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
//    ) {
//        Box {
//// Фон-картинка
//            Image(
//                painter = painterResource(id = R.drawable.ic_baseline_question_mark_24), // 👈 твоя картинка
//                contentDescription = "Сотрудничество",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(RoundedCornerShape(12.dp))
//            )
//
//// Затемнение
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        Brush.horizontalGradient(
//                            colors = listOf(
//                                Color.Black.copy(alpha = 0.55f),
//                                Color.Transparent
//                            )
//                        )
//                    )
//            )
//
//// Текстовый блок
//            Column(
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .padding(horizontal = 14.dp, vertical = 8.dp)
//            ) {
//                Text(
//                    text = "Сотрудничество",
//                    color = Color.White,
//                    style = MaterialTheme.typography.bodyMedium.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 15.sp
//                    )
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "Для сотрудничества с нами прочтите информацию во вкладке",
//                    color = Color(0xFFB0B0B0),
//                    style = MaterialTheme.typography.bodySmall.copy(
//                        fontSize = 12.sp
//                    ),
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//        }
//    }
//}
//@Composable
//fun StoryItemNews(
//    title: String,
//    @DrawableRes previewImageRes: Int,
//    isViewed: Boolean,
//    onClick: () -> Unit
//) {
//    val borderColor = if (isViewed) Color(0xFF4CAF50) else Color(0xFFFF6F00)
//
//    Box(
//        modifier = Modifier
//            .size(82.dp)
//            .clickable { onClick() },
//
//        ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(4.dp)
//        ) {
//            Image(
//                painter = painterResource(id = previewImageRes),
//                contentDescription = title,
//                modifier = Modifier
//                    .size(159.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = title,
//                fontSize = 12.sp,
//                color = Color.White,
//                fontWeight = FontWeight.Medium,
//                maxLines = 1,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
