package com.example.mycar.ui.instructions

import androidx.compose.foundation.layout.Column

import androidx.compose.ui.unit.dp
import com.example.mycar.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun InstructionCard(instruction: Instruction, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(instruction.title, style = MaterialTheme.typography.h6)
            Text(instruction.description, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun InstructionsFragment(instructions: List<Instruction>) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedInstruction by remember { mutableStateOf(Instruction("", "", "")) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(instructions) { instruction ->
            InstructionCard(instruction = instruction) {
                selectedInstruction = instruction
                showDialog = true
            }
        }
    }

    if (showDialog) {
        DetailedInstructionDialog(instruction = selectedInstruction) { showDialog = false }
    }
}

@Composable
fun DetailedInstructionDialog(instruction: Instruction, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(instruction.title) },
        text = { Text(instruction.detailedDescription) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("OK")
            }
        }
    )
}