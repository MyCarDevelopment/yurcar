package com.example.mycar.ui.marketplace

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column

import androidx.compose.ui.unit.dp
import com.example.mycar.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

import androidx.navigation.compose.*

import androidx.compose.foundation.lazy.grid.LazyVerticalGrid


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexMarketplaceFragment() {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("Шины") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val fakeProducts = listOf(
        // 🔥 ШИНЫ (8 товаров)
        Product("Michelin Primacy 4 205/55 R16", "8 500₽", "МагнитогорскАвто", 4.8f, 127, R.drawable.pngtyres, "Шины"),
        Product("Nokian Hakkapeliitta 9 205/55 R16", "12 900₽", "Шинсервис", 4.9f, 89, R.drawable.pngtyres, "Шины"),
        Product("Hankook Ventus S1 205/55 R16", "7 200₽", "Колеса24", 4.6f, 156, R.drawable.pngtyres, "Шины"),
        Product("Bridgestone Turanza T005 205/55", "9 800₽", "Рулим", 4.7f, 203, R.drawable.pngtyres, "Шины"),
        Product("Continental ContiPremiumContact", "11 200₽", "ШинаПлюс", 4.9f, 98, R.drawable.pngtyres, "Шины"),
        Product("Pirelli Cinturato P7 215/55 R16", "10 500₽", "ПиреллиЦентр", 4.8f, 167, R.drawable.pngtyres, "Шины"),
        Product("Goodyear EfficientGrip 195/65 R15", "6 900₽", "ШинныйМир", 4.7f, 234, R.drawable.pngtyres, "Шины"),
        Product("Yokohama BluEarth 205/60 R16", "8 200₽", "ЯпонияШины", 4.6f, 145, R.drawable.pngtyres, "Шины"),

        // 🔥 АКБ (6 товаров)
        Product("АКБ Bosch S5 74Ah", "12 500₽", "АккумуляторПро", 4.9f, 234, R.drawable.avtoelektrik, "АКБ"),
        Product("АКБ Varta Silver Dynamic 74Ah", "13 200₽", "Батарейка", 4.8f, 198, R.drawable.avtoelektrik, "АКБ"),
        Product("АКБ Mutlu Silver Evolution 74Ah", "11 800₽", "ТурецкийАКБ", 4.7f, 156, R.drawable.avtoelektrik, "АКБ"),
        Product("АКБ Exide Premium EA74", "12 900₽", "ЕксайдЦентр", 4.9f, 89, R.drawable.avtoelektrik, "АКБ"),
        Product("АКБ Titan Silver NT74", "10 700₽", "БюджетАКБ", 4.6f, 301, R.drawable.avtoelektrik, "АКБ"),
        Product("АКБ Delta RX74", "11 200₽", "ДельтаМаг", 4.8f, 176, R.drawable.avtoelektrik, "АКБ"),

        // 🔥 МАСЛО (7 товаров)
        Product("Масло Motul 8100 5W40 5L", "6 800₽", "МаслоМаг", 4.7f, 76, R.drawable.maslo, "Масло"),
        Product("Shell Helix Ultra 5W40 4L", "5 900₽", "ШеллОйл", 4.8f, 123, R.drawable.maslo, "Масло"),
        Product("Castrol Edge 5W30 4L", "6 200₽", "КастролСервис", 4.9f, 201, R.drawable.maslo, "Масло"),
        Product("Mobil 1 ESP 5W30 4L", "7 100₽", "МобилОйл", 4.9f, 145, R.drawable.maslo, "Масло"),
        Product("Liqui Moly Synthoil 5W40 5L", "8 900₽", "ЛиквиМоли", 4.8f, 98, R.drawable.maslo, "Масло"),
        Product("Lukoil Genesis Armortech 5W40 4L", "4 800₽", "Лукойл", 4.6f, 389, R.drawable.maslo, "Масло"),
        Product("Total Quartz 9000 5W40 5L", "5 700₽", "ТоталСервис", 4.7f, 167, R.drawable.maslo, "Масло"),

        // 🔥 ДИСКИ (5 товаров)
        Product("Диски R17 реплика VW 5x112", "28 000₽/комплект", "КолесоДиск", 4.8f, 67, R.drawable.tyrechange, "Диски"),
        Product("Диски R16 LS RSK1 5x114.3", "22 500₽/комплект", "ЛитыеДиски", 4.7f, 134, R.drawable.tyrechange, "Диски"),
        Product("Штамповки R15 4x108 Kia", "12 900₽/комплект", "Штамповка", 4.6f, 245, R.drawable.tyrechange, "Диски"),
        Product("Диски R18 MSS M42 5x120 BMW", "35 800₽/комплект", "ПремиумДиски", 4.9f, 45, R.drawable.tyrechange, "Диски"),
        Product("Диски R16 Tech Line 5x114.3", "19 700₽/комплект", "TechLine", 4.8f, 92, R.drawable.tyrechange, "Диски"),

        // 🔥 ФИЛЬТРЫ (6 товаров)
        Product("Фильтр масляный Mann HU719/6X", "890₽", "ФильтрПро", 4.9f, 456, R.drawable.maslo, "Фильтры"),
        Product("Воздушный фильтр Knecht LAK128", "1 200₽", "Кнехт", 4.8f, 312, R.drawable.maslo, "Фильтры"),
        Product("Топливный фильтр Bosch 0.450.004.501", "1 450₽", "БошФильтры", 4.9f, 289, R.drawable.maslo, "Фильтры"),
        Product("Салонный фильтр Mann CU2530", "650₽", "МаннФильтр", 4.7f, 567, R.drawable.maslo, "Фильтры"),
        Product("Комплект фильтров Big Filter", "2 800₽", "КомплектФильтров", 4.8f, 178, R.drawable.maslo, "Фильтры"),
        Product("Масляный фильтр Fram PH3593A", "720₽", "Фрам", 4.7f, 234, R.drawable.maslo, "Фильтры")
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Яндекс Маркет", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { (context as ComponentActivity).finish() }) {
                        Icon(Icons.Default.ArrowBack, "", tint = Color.White)
                    }
                },

            )
        },

    ) { padding ->
        // 🔥 ФИЛЬТРОВАННЫЕ ТОВАРЫ
        val filteredProducts = fakeProducts.filter { product ->
            product.category == selectedTab
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Табы категорий
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf("Шины", "АКБ", "Масло", "Диски", "Фильтры")) { tab ->
                        Surface(
                            modifier = Modifier.clickable { selectedTab = tab },
                            color = if (selectedTab == tab) Color(0xFF2196F3) else Color(0xFF2A2A3E),
                            shape = RoundedCornerShape(20.dp),

                        ) {
                            Text(
                                text = tab,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ✅ ФИЛЬТРОВАННЫЙ список товаров
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredProducts) { product ->  // 🔥 ТОЛЬКО ФИЛЬТРОВАННЫЕ!
                        YandexMarketProductCard(product) {
                            selectedProduct = product
                        }
                    }
                }

                // Кол-во товаров в категории
                Text(
                    text = "Найдено товаров: ${filteredProducts.size}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Модал поверх всего
            selectedProduct?.let { product ->
                YandexProductModal(product) {
                    selectedProduct = null
                }
            }
        }
    }
}

@Composable
fun YandexMarketProductCard(product: Product, onClick: () -> Unit) {
        Surface(  // ✅ Surface вместо Card
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .clip(RoundedCornerShape(20.dp)),  // ✅ Скругление через clip
            color = Color(0xFF2A2A3E),  // ✅ Простой color

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
            // Фото товара
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.shop,
                    color = Color(0xFF2196F3),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, "", tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.rating} (${product.reviews} отз.)",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
            }

            // Цена
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = product.price,
                    color = Color(0xFF4CAF50),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "в корзину",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun YandexProductModal(product: Product, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(24.dp)),
            color = Color(0xFF1E1E2E),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Заголовок
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(product.shop, color = Color(0xFF2196F3), fontSize = 16.sp)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.ArrowBack, "", tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Большое фото
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Цена и кнопка
                Column {
                    Text(product.price, color = Color(0xFF4CAF50), fontSize = 28.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Переход на Яндекс Маркет */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF2196F3)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Купить на Яндекс Маркет", fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val shop: String,
    val rating: Float,
    val reviews: Int = 127,
    val imageRes: Int,
    val category: String  // ✅ НОВОЕ ПОЛЕ!
)

// В preview добавь эти drawables или замени на свои
@Preview
@Composable
fun YandexMarketplacePreview() {
    YandexMarketplaceFragment()
}
