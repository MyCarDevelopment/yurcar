package com.example.mycar.ui.profile

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EditProfileScreen(
                    viewModel = viewModel,
                    onBack = { findNavController().navigateUp() }
                )
            }
        }
    }
}

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var nameState by remember { mutableStateOf(viewModel.fullName.value) }
    var emailState by remember { mutableStateOf(viewModel.email.value) }
    var brandState by remember { mutableStateOf(viewModel.carBrand.value) }
    var modelState by remember { mutableStateOf(viewModel.carModel.value) }
    var statusState by remember { mutableStateOf(viewModel.carStatus.value) }
    var tgState by remember { mutableStateOf(viewModel.igor.value) }

    val statuses = listOf("В продаже авто", "Владею авто", "В поиске нового авто")

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Редактирование профиля", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                backgroundColor = Color(0xFF464647),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEEEEE))
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nameState.text,
                onValueChange = { nameState = nameState.copy(text = it) },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = emailState.text,
                onValueChange = { emailState = emailState.copy(text = it) },
                label = { Text("Email") },
                isError = emailState.text.isNotEmpty() &&
                        !Patterns.EMAIL_ADDRESS.matcher(emailState.text).matches(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = brandState.text,
                onValueChange = { brandState = brandState.copy(text = it) },
                label = { Text("Марка авто") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = modelState.text,
                onValueChange = { modelState = modelState.copy(text = it) },
                label = { Text("Модель авто") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tgState.text,
                onValueChange = { tgState = tgState.copy(text = it) },
                label = { Text("Telegram") },
                isError = tgState.text.isNotEmpty() &&
                        !tgState.text.startsWith("https://t.me/"),
                modifier = Modifier.fillMaxWidth()
            )

            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedTextField(
                    value = statusState.text,
                    onValueChange = {},
                    label = { Text("Статус") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    readOnly = true
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    statuses.forEach { option ->
                        DropdownMenuItem(onClick = {
                            statusState = statusState.copy(text = option)
                            expanded = false
                        }) {
                            Text(option)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val emailValid = emailState.text.isEmpty() ||
                            Patterns.EMAIL_ADDRESS.matcher(emailState.text).matches()
                    val tgValid = tgState.text.isEmpty() || tgState.text.startsWith("https://t.me/")

                    if (!emailValid || !tgValid) {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Проверьте правильность введённых данных")
                        }
                        return@Button
                    }

//                    viewModel.saveData(
//                        fullName = nameState.text,
//                        email = emailState.text,
//                        carBrand = brandState.text,
//                        carModel = modelState.text,
//                        carStatus = statusState.text,
//                        telegram = tgState.text
//                    )

                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Изменения сохранены")
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Сохранить")
            }
        }
    }
}
