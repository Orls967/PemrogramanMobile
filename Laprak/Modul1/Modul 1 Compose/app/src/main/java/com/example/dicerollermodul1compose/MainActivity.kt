package com.example.dicerollermodul1compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceApp()
        }
    }
}

@Composable
fun DiceApp() {

    // 0 = belum di-roll (dadu kosong)
    var dice1 by remember { mutableStateOf(0) }
    var dice2 by remember { mutableStateOf(0) }
    var resultText by remember { mutableStateOf("Tekan Roll!") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Image(
                painter = painterResource(id = getDiceImage(dice1)),
                contentDescription = "Dice 1",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = getDiceImage(dice2)),
                contentDescription = "Dice 2",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = resultText
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            dice1 = Random.nextInt(1, 7)
            dice2 = Random.nextInt(1, 7)

            resultText = if (dice1 == dice2) {
                "Selamat, anda dapat dadu double!"
            } else {
                "Anda belum beruntung!"
            }
        }) {
            Text("Roll")
        }
    }
}

fun getDiceImage(value: Int): Int {
    return when (value) {
        0 -> R.drawable.dice_0
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}