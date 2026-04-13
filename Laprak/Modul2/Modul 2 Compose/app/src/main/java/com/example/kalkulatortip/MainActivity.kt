package com.example.kalkulatortip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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

    fun isValidInput(input: String): Boolean {
        return input.matches(Regex("^\\d*(\\.|,)?\\d*\$"))
    }

    val percent = when (selectedTip) {
        "15%" -> 0.15
        "18%" -> 0.18
        else -> 0.20
    }

    val cleanBill = bill.replace(",", ".")

    val billValue = if (
        cleanBill.isEmpty() ||
        cleanBill == "." ||
        cleanBill.endsWith(".")
    ) {
        0.0
    } else {
        cleanBill.toDoubleOrNull() ?: 0.0
    }

    val tip = if (roundUp) {
        ceil(billValue * percent)
    } else {
        billValue * percent
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

            Box(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable { expanded = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        selectedTip,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
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