package com.example.mycar.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mycar.R

/**
 * ProfileFragment — показывает профиль пользователя (только просмотр).
 * Редактирование вынесено в отдельный EditProfileFragment (вызывается кнопкой "Редактировать").
 *
 * Зависит от ProfileViewModel (AndroidViewModel) который хранит/подгружает данные из SharedPreferences.
 */
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ProfileScreen(
                    viewModel = viewModel,
                    onEditClick = {
                        // пока шаблонно — просто заглушка перехода
                        // если есть навигация:
                        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)

                        // если навигации нет — просто заглушка:
                        // Toast.makeText(context, "Переход в редактирование", Toast.LENGTH_SHORT).show()
                    },
                    onLogout = {
                        // просто заглушка, без Firebase
                        // можно, например, вызвать navigateToLogin или finish
                    }
                )
            }
        }
    }

    override fun onResume() {
        viewModel.reloadData()
        super.onResume()
    }



/* ---------------- Composables ---------------- */

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit,
    onLogout: () -> Unit
) {
    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val carBrand by viewModel.carBrand.collectAsState()
    val carModel by viewModel.carModel.collectAsState()
    val status by viewModel.carStatus.collectAsState()
    val context = LocalContext.current

    var menuExpanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Цвета / палитра
    val headerStart = Color(0xFF1E2A3A)
    val headerEnd = Color(0xFF223546)
    val cardBg = Color(0xFFFAFAFC)
    val accent = Color(0xFFFF6F00) // акцентный цвет

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль", color = Color.White) },
                backgroundColor = headerStart,
                actions = {
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            menuExpanded = false
                            onLogout()
                        }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.Red)
                            Spacer(Modifier.width(8.dp))
                            Text("Выйти")
                        }
                        DropdownMenuItem(onClick = {
                            menuExpanded = false
                            onEditClick()
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Редактировать")
                        }
                    }
                }
            )
        },
        backgroundColor = Color(0xFFF3F4F6)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header with gradient, avatar, name, email, chips
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        brush = Brush.verticalGradient(listOf(headerStart, headerEnd))
                    )
            ) {
                // Content inside header
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Avatar block
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF334155)),
                        contentAlignment = Alignment.Center
                    ) {
                        val initial = fullName.text.trim().takeIf { it.isNotEmpty() }?.firstOrNull()?.uppercaseChar() ?: 'U'
                        Text(
                            text = initial.toString(),
                            fontSize = 36.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 6.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = fullName.text.ifEmpty { "Имя не указано" },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = email.text.ifEmpty { "Почта не указана" },
                            fontSize = 14.sp,
                            color = Color(0xFFCAD6E0)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // chips: status and car
                        Row {
                            Chip(label = status.text, icon = Icons.Default.Info)
                            Spacer(modifier = Modifier.width(8.dp))
                            Chip(label = (carBrand.text.ifEmpty { "Марка" } + " " + carModel.text.ifEmpty { "" }.trim()), icon = Icons.Default.Favorite)
                        }
                    }
                }
            }

            // Main card overlapping header
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-28).dp) // overlap effect
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 8.dp,
                    backgroundColor = cardBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Row: header label + edit button (secondary)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Информация профиля", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF111827))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Здесь отображаются ваши данные. Чтобы изменить — нажмите Редактировать.", fontSize = 12.sp, color = Color(0xFF6B7280))
                            }
                            OutlinedButton(onClick = { onEditClick() }) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text("Редактировать")
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Fields list
                        ProfileFieldRow(icon = Icons.Default.Person, label = "ФИО", value = fullName.text.ifEmpty { "—" })
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileFieldRow(icon = Icons.Default.Email, label = "Почта", value = email.text.ifEmpty { "—" })
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileFieldRow(icon = Icons.Default.Favorite, label = "Марка / Модель", value = (carBrand.text + " " + carModel.text).trim().ifEmpty { "—" })
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileFieldRow(icon = Icons.Default.Info, label = "Статус", value = status.text.ifEmpty { "—" })
                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Telegram row with click
                        val tg = (viewModel.igor.collectAsState().value).text.ifEmpty { viewModel.email.collectAsState().value.text } // пример: если у тебя есть telegram в igor/telegram - поправь
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // open telegram if link present
                                    val link = if (tg.isNotBlank()) tg else ""
                                    if (link.isNotBlank()) {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                        context.startActivity(intent)
                                    }
                                }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = Color(0xFF6B7280))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Telegram", fontSize = 13.sp, color = Color(0xFF6B7280))
                                Text(tg.ifEmpty { "Не указано" }, fontSize = 15.sp, color = Color(0xFF111827))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Big action button
                        Button(
                            onClick = { onEditClick() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = accent)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Редактировать профиль", color = Color.White)
                        }
                    }
                }
            }


            }

            // Logout confirmation dialog
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Выйти из аккаунта") },
                    text = { Text("Вы уверены, что хотите выйти?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            onLogout()
                        }) {
                            Text("Выйти", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showLogoutDialog = false }) {
                            Text("Отмена")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun Chip(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 2.dp,
        backgroundColor = Color(0xFF2B3947),
        modifier = Modifier
            .height(30.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, color = Color.White, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ProfileFieldRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color(0xFF9CA3AF))
            Text(text = value, fontSize = 15.sp, color = Color(0xFF111827))
        }
    }
}