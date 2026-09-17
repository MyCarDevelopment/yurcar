package com.example.mycar.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.fragment.app.Fragment
import com.example.mycar.R
import com.example.mycar.databinding.FragmentHomeBinding
import com.example.mycar.ui.*
import com.example.mycar.ui.instructions.InstructionsActivity
import com.example.mycar.ui.checkfines.FinesFragment
import com.example.mycar.ui.services.ServicesActivity
import com.example.mycar.ui.marketplace.MarketplaceFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.mycar.AgreementActivity
import com.example.mycar.LegalDocumentsActivity
import com.example.mycar.ui.indicators.IndicatorsActivity
import com.example.mycar.ui.mycarfragment.MyCarActivity

class HomeFragment : Fragment() {

    private val cardBackgrounds = mapOf(
        CardType.BUY_CAR to R.drawable.test,
        CardType.TIRES to R.drawable.ind7,
        CardType.INSTRUCTIONS to R.drawable.pomoshnadoroge,
        CardType.SERVICES to R.drawable.avtoelektrik,
        CardType.SIXTH to R.drawable.maslo,
        CardType.SELL_CAR to R.drawable.fines
    )

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private fun hasUserAgreed(): Boolean {
        val sharedPref = requireContext().getSharedPreferences("app_agreement", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("accepted", false)
    }

    private fun saveUserAgreement() {
        val sharedPref = requireContext().getSharedPreferences("app_agreement", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("accepted", true).apply()
    }

    private fun openAgreement() {
        startActivity(Intent(requireContext(), AgreementActivity::class.java))
    }

    @Composable
    fun AgreementDialog(
        onOpenAgreement: () -> Unit,
        onAccepted: () -> Unit
    ) {
        var checked by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { },
            backgroundColor = HomeColors.graphite,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "Пользовательское соглашение",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    val annotatedText = buildAnnotatedString {
                        append("Я принимаю условия ")
                        pushStringAnnotation(tag = "AGREEMENT", annotation = "open")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF97CEF8),
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Пользовательского соглашения")
                        }
                        pop()
                        append(" и даю согласие на обработку предоставленных данных.")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = HomeColors.accentOrange,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.White
                            )
                        )

                        ClickableText(
                            text = annotatedText,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp
                            ),
                            onClick = { offset ->
                                annotatedText
                                    .getStringAnnotations("AGREEMENT", offset, offset)
                                    .firstOrNull()
                                    ?.let { onOpenAgreement() }
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onAccepted,
                    enabled = checked
                ) {
                    Text(
                        text = "Продолжить",
                        color = if (checked) HomeColors.accentOrange else Color.Gray
                    )
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Home"
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                var showAgreementDialog by remember { mutableStateOf(!hasUserAgreed()) }
                HomeScreenContent(
                    backgrounds = cardBackgrounds,
                    onCardClick = ::navigateToActivity,
                    onTelegramClick = { openTelegram() },
                    onOpenLegalDocuments = {
                        startActivity(Intent(requireContext(), LegalDocumentsActivity::class.java))
                    }
                )

                if (showAgreementDialog) {
                    AgreementDialog(
                        onAccepted = {
                            saveUserAgreement()
                            showAgreementDialog = false
                        }
                    )
                }
            }
        }
    }

    private fun navigateToActivity(cardType: CardType) {
        val context = requireContext()
        when (cardType) {
            CardType.BUY_CAR -> startActivity(Intent(requireContext(), MyCarActivity::class.java))
            CardType.SELL_CAR -> startActivity(Intent(context, FinesFragment::class.java))
            CardType.TIRES -> startActivity(Intent(context, IndicatorsActivity::class.java))
            CardType.SERVICES -> startActivity(Intent(context, ServicesActivity::class.java))
            CardType.INSTRUCTIONS -> startActivity(
                Intent(
                    context,
                    InstructionsActivity::class.java
                )
            )

            CardType.SIXTH -> startActivity(Intent(context, MarketplaceFragment::class.java))
        }
    }

    private fun openTelegram() {
        val context = requireContext()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/mycarcommunity"))
        context.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// 🎨 ЦВЕТА
object HomeColors {
    val darkBackground = Color(0xFF1E2428)
    val darkGray = Color(0xFF262D33)
    val graphite = Color(0xFF2E3840)
    val accentOrange = Color(0xFF343F48)
}

@Composable
fun HomeScreenContent(
    backgrounds: Map<CardType, Int>,
    onCardClick: (CardType) -> Unit,
    onTelegramClick: () -> Unit,
    onOpenLegalDocuments: () -> Unit,

) {
    var selectedStoryIndex by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HomeColors.darkGray, HomeColors.darkBackground)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 95.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { StoriesNews(onStorySelected = { selectedStoryIndex = it }) }
//            item { HomeBanner(onClick = onTelegramClick) }
            item {
                TopGridMenu(
                    backgrounds = backgrounds,
                    onCardClick = onCardClick
                )
            }


            item {
                ExtraGridMenu(
                    cards = listOf(CardType.SERVICES, CardType.SIXTH),
                    backgrounds = mapOf(
                        CardType.SERVICES to R.drawable.avtoelektrik,
                        CardType.SIXTH to R.drawable.maslo
                    ),
                    onCardClick = onCardClick
                )
            }
            item {
                BigRectangleCard(
                    cardType = CardType.SELL_CAR,
                    backgroundRes = R.drawable.fines,
                    onClick = { onCardClick(CardType.SELL_CAR) }
                )
            }
            item { TelegramInvite(onClick = onTelegramClick) }
            item { LegalDocumentsCard(onClick = onOpenLegalDocuments) }
        }


        if (selectedStoryIndex >= 0) {
            StoryViewer(
                stories = demoStories,
                initialIndex = selectedStoryIndex,
                onDismiss = { selectedStoryIndex = -1 }
            )
        }
    }
}

// 📰 СТОРИС
@Composable
fun StoriesNews(onStorySelected: (Int) -> Unit) {
    val stories = listOf(
        "ГИБДД" to Story("Story 1", R.drawable.news, R.drawable.st2),
        "ПДД" to Story("Story 2", R.drawable.newsf, R.drawable.st3),
        "Параллельный импорт" to Story("Story 3", R.drawable.story1, R.drawable.st4),
        "ОСАГО" to Story("Story 1", R.drawable.newss, R.drawable.st5),
        "Сезонные гайды" to Story("Story 2", R.drawable.ind38, R.drawable.st1)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = HomeColors.graphite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = "📰 Новости",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(stories.size) { index ->
                    val (title, story) = stories[index]
                    StoryItemNews(
                        title = title,
                        previewImageRes = story.previewImageRes,
                        isViewed = index < 2,
                        onClick = { onStorySelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun StoryItemNews(
    title: String,
    @DrawableRes previewImageRes: Int,
    isViewed: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isViewed) Color(0xFF4CAF50) else HomeColors.accentOrange

    Card(
        modifier = Modifier
            .width(86.dp)
            .height(108.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = HomeColors.graphite,
        elevation = 4.dp,
        border = BorderStroke(2.dp, borderColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = previewImageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// 🔥 БАННЕР
@Composable
fun HomeBanner(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "🔥 Специальное предложение!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Нажмите, чтобы узнать подробности",
                    color = HomeColors.accentOrange,
                    fontSize = 13.sp
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Подробнее",
                tint = HomeColors.accentOrange,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

// 📱 ГРИДЫ (Material API - работает с вашей библиотекой)
@Composable
fun TopGridMenu(
    backgrounds: Map<CardType, Int>,
    onCardClick: (CardType) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        // ПРЯМОУГОЛЬНАЯ КАРТОЧКА (высота = 3 ячейки)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp) // ← тот же размер что был у 3 ячеек
                .padding(6.dp)
                .clickable { onCardClick(CardType.BUY_CAR) },
            shape = RoundedCornerShape(14.dp),
            elevation = 6.dp,
            backgroundColor = HomeColors.graphite
        ) {
            ImageCardContent(
                backgroundRes = backgrounds[CardType.BUY_CAR] ?: R.drawable.fines,
                cardType = CardType.BUY_CAR,
                textAlignment = Alignment.CenterStart,
                iconSize = 36.dp,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ExtraGridMenu(
    cards: List<CardType>,
    backgrounds: Map<CardType, Int>,
    onCardClick: (CardType) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cards.forEach { cardType ->
                Box(modifier = Modifier.weight(1f)) {
                    ExtraCardItem(
                        cardType = cardType,
                        backgroundRes = backgrounds[cardType] ?: R.drawable.fines,
                        onClick = { onCardClick(cardType) }
                    )
                }
            }
        }
    }
}

// 🃏 КАРТОЧКИ
@Composable
fun GridCardItem(cardType: CardType, @DrawableRes backgroundRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        backgroundColor = HomeColors.graphite
    ) {
        ImageCardContent(
            backgroundRes = backgroundRes,
            cardType = cardType,
            textAlignment = Alignment.BottomStart
        )
    }
}

@Composable
fun ExtraCardItem(
    cardType: CardType,
    @DrawableRes backgroundRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = 6.dp,
        backgroundColor = Color.Transparent
    ) {
        ImageCardContent(
            backgroundRes = backgroundRes,
            cardType = cardType,
            textAlignment = Alignment.BottomStart,
            iconSize = 32.dp
        )
    }
}

@Composable
fun BigRectangleCard(cardType: CardType, @DrawableRes backgroundRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        ImageCardContent(
            backgroundRes = backgroundRes,
            cardType = cardType,
            textAlignment = Alignment.TopStart,
            iconSize = 36.dp,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun ImageCardContent(
    @DrawableRes backgroundRes: Int,
    cardType: CardType,
    textAlignment: Alignment,
    iconSize: Dp = 24.dp,
    fontSize: TextUnit = 15.sp
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = cardType.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent)
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(textAlignment)
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(id = cardType.icon),
                contentDescription = cardType.title,
                tint = Color.White,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = cardType.title,
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// 📱 TELEGRAM + COOPERATION
@Composable
fun TelegramInvite(onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Присоединяйтесь к нашему сообществу в Telegram!",
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Вступить в сообщество",
                    color = Color(0xFF97CEF8),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
            Image(
                painter = painterResource(id = R.drawable.tg),
                contentDescription = "Telegram",
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun CooperationCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        backgroundColor = HomeColors.darkGray
    ) {
        ImageCardContent(
            backgroundRes = R.drawable.ic_baseline_question_mark_24,
            cardType = CardType.SERVICES,
            textAlignment = Alignment.CenterStart,
            iconSize = 24.dp
        )
    }
}

// 🗂️ ДАННЫЕ
private val demoStories = listOf(
    Story("Story 1", R.drawable.ind38, R.drawable.st2),
    Story("Story 2", R.drawable.ind38, R.drawable.st3),
    Story("Story 3", R.drawable.ind38, R.drawable.st4),
    Story("Story 4", R.drawable.ind38, R.drawable.st5),
    Story("Story 5", R.drawable.ind38, R.drawable.st1)
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryViewer(
    stories: List<Story>,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState { stories.size }
    var isPaused by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var verticalOffset by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage, isPaused) {
        if (!isPaused) {
            progress = 0f
            repeat(100) {
                progress = it / 100f
                delay(50)
            }
            val nextPage = pagerState.currentPage + 1
            if (nextPage < stories.size) {
                pagerState.animateScrollToPage(nextPage)
            } else {
                onDismiss()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPaused = true
                        tryAwaitRelease()
                        isPaused = false
                    },
                    onTap = {
                        coroutineScope.launch {
                            val nextPage = pagerState.currentPage + 1
                            if (nextPage < stories.size) {
                                pagerState.animateScrollToPage(nextPage)
                            } else {
                                onDismiss()
                            }
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        verticalOffset += dragAmount
                    },
                    onDragEnd = {
                        if (verticalOffset.absoluteValue > 300f) {
                            onDismiss()
                        } else {
                            verticalOffset = 0f
                        }
                    }
                )
            }
            .offset { IntOffset(0, verticalOffset.roundToInt()) }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = stories[page].contentImageRes),
                contentDescription = stories[page].title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Прогресс
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.TopCenter)
                .background(Color.Gray.copy(0.5f), RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(Color.Cyan, RoundedCornerShape(2.dp))
            )
        }

        Icon(
            painter = painterResource(R.drawable.ind7), // ← drawable вместо ImageVector
            contentDescription = "Закрыть",
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.TopEnd)
                .padding(20.dp)
                .clickable { onDismiss() }
        )
    }
}



@Composable
fun LegalDocumentsCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        backgroundColor = HomeColors.graphite
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Политика данных и документы",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Пользовательское соглашение и другие документы",
                    color = Color(0xFF97CEF8),
                    fontSize = 14.sp
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Открыть документы",
                tint = HomeColors.accentOrange,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}


//dsds


@Composable
fun AgreementDialog(
    onAccepted: () -> Unit
) {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }
    var checked3 by remember { mutableStateOf(false) }

    var openedDocument by remember { mutableStateOf<String?>(null) }

    val allChecked = checked1 && checked2 && checked3

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text("Пользовательское соглашение")
        },
        text = {
            Column {
                AgreementRow(
                    checked = checked1,
                    onCheckedChange = { checked1 = it },
                    prefix = "Ознакомлен с условиями пользовательского соглашения. ",
                    linkText = "Пользовательское соглашение",
                    onLinkClick = { openedDocument = "agreement.txt" }
                )

                Spacer(modifier = Modifier.height(12.dp))

                AgreementRowWithTwoLinks(
                    checked = checked2,
                    onCheckedChange = { checked2 = it },
                    onOpenConsent = { openedDocument = "agreement.txt" },
                    onOpenPrivacy = { openedDocument = "agreement.txt" }
                )

                Spacer(modifier = Modifier.height(12.dp))

                AgreementRowMailingWithTwoLinks(
                    checked = checked3,
                    onCheckedChange = { checked3 = it },
                    onOpenInformation = { openedDocument = "information.txt" },
                    onOpenMailing = { openedDocument = "mailing.txt" }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAccepted,
                enabled = allChecked
            ) {
                Text("Продолжить")
            }
        }
    )

    openedDocument?.let { fileName ->
        DocumentDialog(
            assetFileName = fileName,
            onDismiss = { openedDocument = null }
        )
    }
}@Composable
fun AgreementRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    prefix: String,
    linkText: String,
    onLinkClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        val annotatedText = buildAnnotatedString {
            append(prefix)

            pushStringAnnotation(tag = "DOC", annotation = "DOC")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1565C0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(linkText)
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            style = LocalTextStyle.current.copy(color = Color.Black),
            onClick = { offset ->
                annotatedText
                    .getStringAnnotations(tag = "DOC", start = offset, end = offset)
                    .firstOrNull()
                    ?.let { onLinkClick() }
            }
        )
    }
}
@Composable
fun DocumentDialog(
    assetFileName: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val documentText = remember(assetFileName) {
        context.assets.open(assetFileName).bufferedReader().use { it.readText() }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Документ")
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = documentText)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}


@Composable
fun AgreementRowWithTwoLinks(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onOpenConsent: () -> Unit,
    onOpenPrivacy: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        val annotatedText = buildAnnotatedString {
            append("Даю согласие на обработку персональных данных, с ")

            pushStringAnnotation(tag = "CONSENT", annotation = "consent")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1565C0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Согласием")
            }
            pop()

            append(" и ")

            pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1565C0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Политикой в отношении обработки персональных данных")
            }
            pop()

            append(" (Политикой конфиденциальности) Оператора ознакомлен (-на)")
        }

        ClickableText(
            text = annotatedText,
            style = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.weight(1f),
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "CONSENT",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onOpenConsent()
                    return@ClickableText
                }

                annotatedText.getStringAnnotations(
                    tag = "PRIVACY",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onOpenPrivacy()
                }
            }
        )
    }
}

@Composable
fun AgreementRowMailingWithTwoLinks(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onOpenInformation: () -> Unit,
    onOpenMailing: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        val annotatedText = buildAnnotatedString {
            append("Даю согласие на получение ")

            pushStringAnnotation(tag = "INFO", annotation = "information")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1565C0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("информационной")
            }
            pop()

            append(" и рекламной ")

            pushStringAnnotation(tag = "MAILING", annotation = "mailing")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1565C0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("рассылки")
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            style = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.weight(1f),
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "INFO",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onOpenInformation()
                    return@ClickableText
                }

                annotatedText.getStringAnnotations(
                    tag = "MAILING",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onOpenMailing()
                }
            }
        )
    }
}