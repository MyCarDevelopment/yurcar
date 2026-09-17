package com.example.mycar.ui.checkfines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.alpha
import androidx.activity.ComponentActivity

@Composable
fun CheckFinesFragment() {
    var selectedOption by remember { mutableStateOf("Гос. номер авто") }
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val isButtonEnabled = inputText.text.isNotEmpty()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Основной контент с размытием
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF2196F3), Color(0xFF03A9F4))
                    )
                )
                .padding(16.dp)
                .blur(radius = 4.dp)
                .alpha(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Проверка штрафов ГИБДД",
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White,
                    fontSize = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ChoiceChip(
                    text = "Гос. номер авто",
                    selected = selectedOption == "Гос. номер авто",
                    onClick = { selectedOption = "Гос. номер авто"; inputText = TextFieldValue("") }
                )
                Spacer(modifier = Modifier.width(16.dp))
                ChoiceChip(
                    text = "Номер СТС",
                    selected = selectedOption == "Номер СТС",
                    onClick = { selectedOption = "Номер СТС"; inputText = TextFieldValue("") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = {
                    Text(
                        text = if (selectedOption == "Гос. номер авто") "Введите гос. номер" else "Введите номер СТС",
                        color = Color.White
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.White,
                    textColor = Color.White
                ),
                visualTransformation = if (selectedOption == "Гос. номер авто") VisualTransformation.None else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Логика проверки штрафов */ },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray,
                    contentColor = Color.White.copy(alpha = 0.7f)
                )
            ) {
                Text(text = "Проверить", fontSize = 18.sp)
            }
        }

        // Диалог БЕЗ стрелки, только кнопка "На главную"
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(32.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⏳ Функция в разработке",
                        style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                        color = Color(0xFF2196F3)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Проверка штрафов ГИБДД появится в ближайшем обновлении.\nСпасибо за терпение!",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Только кнопка "На главную"
                    Button(
                        onClick = {
                            (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2196F3),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("На главную", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChoiceChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(50),
        color = if (selected) Color(0xFF4659E4) else Color(0xFFC5B0B7),
        onClick = onClick
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckFinesFragmentPreview() {
    CheckFinesFragment()
}
