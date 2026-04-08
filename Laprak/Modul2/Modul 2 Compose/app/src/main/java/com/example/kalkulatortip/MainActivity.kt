package com.example.kalkulatortip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipApp()
        }
    }
}

@Composable
fun TipApp() {

    var bill by remember { mutableStateOf("") }
    var selectedTip by remember { mutableStateOf("15%") }
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val tipOptions = listOf("15%", "18%", "20%")

    // ===== VALIDASI INPUT =====
    fun isValidInput(input: String): Boolean {
        return input.matches(Regex("^\\d*(\\.|,)?\\d*\$"))
    }

    // ===== HITUNG TIP =====
    val percent = when (selectedTip) {
        "15%" -> 0.15
        "18%" -> 0.18
        else -> 0.20
    }

    val billValue = bill
        .replace(",", ".")
        .toDoubleOrNull() ?: 0.0

    var tip = billValue * percent

    if (roundUp) {
        tip = ceil(tip)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(20.dp)
    ) {

        Text(
            text = "Calculate Tip",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ===== BILL CARD =====
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEBEE), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                "Bill Amount",
                color = Color.DarkGray,
                style = MaterialTheme.typography.titleMedium
            )

            TextField(
                value = bill,
                onValueChange = {
                    if (isValidInput(it)) {
                        bill = it
                    }
                },
                placeholder = {
                    Text(
                        "Masukkan jumlah bill",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TIP CARD =====
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEBEE), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                "Tip Percentage",
                color = Color.DarkGray,
                style = MaterialTheme.typography.titleMedium
            )

            Box {
                TextButton(onClick = { expanded = true }) {
                    Text(
                        selectedTip,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tipOptions.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    it,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                selectedTip = it
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== SWITCH =====
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Round up tip?",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFE91E63),
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tip Amount: $%.2f".format(tip),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}