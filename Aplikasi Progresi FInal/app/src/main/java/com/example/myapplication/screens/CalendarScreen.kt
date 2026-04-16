package com.example.myapplication.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.components.RewardCard
import java.time.LocalDate

@Composable
fun CalendarScreen(navController: NavController, context: Context) {

    val today = LocalDate.now().dayOfMonth
    val streakDays = (0..5).map { today - it }

    val weeks = listOf(
        (1..7).toList(),
        (8..14).toList(),
        (15..21).toList(),
        (22..28).toList(),
        listOf(29, 30, 0, 0, 0, 0, 0)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    "Calendar 🔥",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // STREAK INFO
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {

                        Text("🔥 Streak sekarang: 6 hari", fontWeight = FontWeight.Bold)

                        LinearProgressIndicator(
                            progress = 6f / 7f,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("1 hari lagi menuju reward: 250 XP")
                        Text("Next reward: 30 hari → Peci Premium")
                    }
                }
            }

            // CALENDAR GRID
            items(weeks) { week ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    week.forEach { day ->

                        if (day == 0) {
                            Spacer(Modifier.size(50.dp))
                        } else {

                            Card(
                                modifier = Modifier.size(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        day == today -> MaterialTheme.colorScheme.secondary
                                        day in streakDays -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (day in streakDays) "🔥$day" else "$day",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // REWARD
            item {
                Text(
                    "🎁 Reward Streak",
                    fontWeight = FontWeight.Bold
                )
            }

            item { RewardCard("7 hari streak", "250 XP") }
            item { RewardCard("30 hari streak", "Peci Premium") }
            item { RewardCard("100 hari streak", "Motor Vario 160") }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text("Kembali")
        }
    }
}