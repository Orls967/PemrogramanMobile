package com.example.myapplication.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.components.RewardCard
import java.time.LocalDate

@Composable
fun CalendarScreen(navController: NavController, context: Context) {

    val today = LocalDate.now().dayOfMonth
    val streakDays = (0..5).map { today - it }

    var selectedDayForDialog by remember { mutableStateOf<Int?>(null) }

    val hardcodedTasks = mapOf(
        today to listOf("Menunggu Task Hari Ini"),
        today - 1 to listOf("Project App "),
        today - 2 to listOf("Workout Dan Baca Buku"),
        today - 3 to listOf("Belajar Compose"),
        today - 4 to listOf("Workout"),
        today - 5 to listOf("Belajar Kotlin")
    )

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

                        Spacer(modifier = Modifier.height(8.dp))
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
                            val isStreakDay = day in streakDays

                            Card(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable(
                                        enabled = isStreakDay,
                                        onClick = { selectedDayForDialog = day }
                                    ),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        day == today -> MaterialTheme.colorScheme.secondary
                                        isStreakDay -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = if (isStreakDay) "🔥\n$day" else "$day",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
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
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Kembali")
        }

        selectedDayForDialog?.let { day ->
            val tasksForDay = hardcodedTasks[day] ?: listOf("Tidak ada data task untuk hari ini.")

            val isToday = day == today

            AlertDialog(
                onDismissRequest = { selectedDayForDialog = null },
                title = {
                    Text(text = "Riwayat Task - Hari $day")
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        tasksForDay.forEach { task ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val icon = if (isToday) "⏳" else "✅"

                                Text(text = "$icon ", modifier = Modifier.padding(end = 8.dp))
                                Text(text = task)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedDayForDialog = null }) {
                        Text("Tutup")
                    }
                }
            )
        }
    }
}
