package com.example.mycar.ui.services

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column

import androidx.compose.ui.unit.dp
import com.example.mycar.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.mycar.ui.services.Service
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController() // Создаем NavHostController

    MainNavHost(navController = navController)
}
@Composable
fun HomeScreen(navController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(services) { service ->
            ServiceCard(service = service, onClick = {
                navController.navigate("serviceDetails/${service.id}")
            })
        }
    }
}
@Composable
fun ServiceCard(service: com.example.mycar.ui.services.Service, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Ограничиваем ширину карточки
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = 16.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp) // Высота изображения
                .clip(RoundedCornerShape(16.dp)) // Округленные углы
        ) {
            // Картинка фоном
            Image(
                painter = painterResource(id = service.imageResId),
                contentDescription = "Image for ${service.title}",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop // Чтобы изображение покрывало весь контейнер
            )

            // Тусклый слой поверх картинки
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Добавляем полупрозрачный черный фон
            )

            // Наложение текста сверху
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart) // Размещение текста внизу
            ) {
                // Заголовок услуги
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        color = Color.White // Белый цвет текста для контраста
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Описание услуги
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f) // Белый с полупрозрачностью
                    )
                )
            }
        }
    }
}




@Composable
fun ServiceDetailsScreen(serviceId: Int?) {
    val service = services.find { it.id == serviceId }
    var searchText by remember { mutableStateOf("") }

    // Фильтрация компаний по метро и адресу
    val filteredCompanies = remember(searchText) {
        service?.companies?.filter {
            it.metroStation.contains(searchText, ignoreCase = true) ||
                    it.address.contains(searchText, ignoreCase = true)
        } ?: emptyList()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Поле для поиска
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Введите название метро или адрес") },
            singleLine = true,
            maxLines = 1
        )

        // Заголовок услуги
        service?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                item {
                    // Заголовок услуги
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 20.sp
                            ),
                            color = MaterialTheme.colors.primary
                        )
                    }

                    // Заголовок для списка компаний
                    Text(
                        text = "Компании, предоставляющие услугу:",
                        style = MaterialTheme.typography.body2.copy(
                            fontSize = 16.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                // Список компаний
                items(filteredCompanies) { company ->
                    CompanyCard(company)
                }
            }
        } ?: run {
            // Сообщение об отсутствии услуги
            Text(
                text = "Услуга не найдена",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Если список компаний пуст
        if (filteredCompanies.isEmpty() && searchText.isNotEmpty()) {
            Text(
                text = "Нет компаний, соответствующих вашему запросу.",
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



@Composable
fun CompanyCard(company: Company) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(company.name, style = MaterialTheme.typography.h6)


            Text(
                text = company.address,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    val uri = Uri.parse("yandexmaps://maps.yandex.ru/?text=${Uri.encode(company.address)}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: ActivityNotFoundException) {
                        val browserUri = Uri.parse("https://maps.yandex.ru/?text=${Uri.encode(company.address)}")
                        context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
                    }
                }
            )

            Text("Телефон: ${company.phone}", style = MaterialTheme.typography.body2)
            Text("Время работы: ${company.workingHours}", style = MaterialTheme.typography.body2)

            Spacer(modifier = Modifier.height(2.dp))

            // Блок с метро и цветным кружком
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(company.metroColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text("Метро: ${company.metroStation}", style = MaterialTheme.typography.body2)
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text("Услуги: ${company.services}", style = MaterialTheme.typography.body2)
            Text("Цена: ${company.price}", style = MaterialTheme.typography.body2)
        }
    }
}



@Composable
fun MainNavHost(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = when (currentRoute) {
                        "home" -> "Услуги"
                        "serviceDetails/{serviceId}" -> "Список компаний"
                        else -> "Название приложения" // Можно задать общее название
                    })
                }
            )
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") { HomeScreen(navController) }
            composable("serviceDetails/{serviceId}") { backStackEntry ->
                val serviceId = backStackEntry.arguments?.getString("serviceId")?.toIntOrNull()
                ServiceDetailsScreen(serviceId = serviceId)
            }
        }
    }
}
