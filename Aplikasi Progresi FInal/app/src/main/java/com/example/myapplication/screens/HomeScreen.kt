package com.example.myapplication.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Task
import java.time.LocalDate

@Composable
fun HomeScreen(navController: NavController, context: Context) {

    val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    var exp by remember { mutableStateOf(prefs.getInt("exp", 60)) }
    var level by remember { mutableStateOf(prefs.getInt("level", 5)) }
    var streak by remember { mutableStateOf(6) }

    var showDialog by remember { mutableStateOf(false) }
    var showHistory by remember { mutableStateOf(false) }

    // poin 1
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task("Belajar Compose", "Medium", "2026-04-01", 50),
                Task("Workout", "Easy", "2026-04-02", 25),
                Task("Project App", "Hard", "2026-04-03", 150)
            )
        )
    }

    val history = listOf(
        "Belajar Kotlin | +50 XP | 2026-04-01",
        "Workout | +25 XP | 2026-04-02",
        "Baca Buku | +150 XP | 2026-04-03"
    )

    fun checkLevelUp() {
        while (exp >= 100) {
            exp -= 100
            level++
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 🔥 TITLE
            item {
                Text(
                    "Progresi",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // 🔥 LEVEL CARD
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color(0xFFEDE7F6))
                ) {
                    Column(Modifier.padding(18.dp)) {

                        Text(
                            "Level $level",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = exp / 100f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text("EXP: $exp / 100")
                    }
                }
            }

            // 🔥 STREAK CARD
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color(0xFFF3E5F5))
                ) {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            "🔥 Streak: $streak hari",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("1 hari lagi menuju reward 🎁")
                    }
                }
            }

            // 🔥 BUTTON SECTION
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    val btnModifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(55.dp)

                    Button(
                        onClick = { showDialog = true },
                        modifier = btnModifier,
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("📜 Buat Task", style = MaterialTheme.typography.bodyLarge)
                    }

                    Button(
                        onClick = { navController.navigate("calendar") },
                        modifier = btnModifier,
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("📅 Lihat Streak Calendar", style = MaterialTheme.typography.bodyLarge)
                    }

                    Button(
                        onClick = { showHistory = true },
                        modifier = btnModifier,
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("📜 Riwayat Task", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            // 🔥 TASK LIST
            items(tasks) { task ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color(0xFFEDE7F6))
                ) {
                    Column(Modifier.padding(18.dp)) {

                        Text(
                            task.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("${task.difficulty} (+${task.exp} XP)")

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = { // poin 4
                                exp += task.exp
                                checkLevelUp()
                                tasks = tasks - task
                            },
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text("Task Selesai")
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(90.dp))
            }
        }

        // 🔥 SIGN OUT
        Button(
            onClick = {
                prefs.edit().clear().apply()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("🚪 Sign Out")
        }
    }

    if (showDialog) {

        var title by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var difficulty by remember { mutableStateOf("Easy") }

        val options = listOf("Easy", "Medium", "Hard")

        AlertDialog(
            onDismissRequest = { showDialog = false },

            confirmButton = {
                Button(onClick = {

                    val expValue = when (difficulty) {
                        "Easy" -> 25
                        "Medium" -> 50
                        else -> 150
                    }
                    //poin 3
                    tasks = tasks + Task(
                        title,
                        difficulty,
                        LocalDate.now().toString(),
                        expValue
                    )

                    showDialog = false

                }) {
                    Text("Simpan")
                }
            },

            dismissButton = {
                Button({ showDialog = false }) {
                    Text("Batal")
                }
            },

            title = { Text("Buat Task") },

            text = {
                Column {

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Nama Task") }
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(onClick = { expanded = true }) {
                        Text("Difficulty: $difficulty")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    difficulty = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        )
    }

    if (showHistory) {
        AlertDialog(
            onDismissRequest = { showHistory = false },

            confirmButton = {
                Button(onClick = { showHistory = false }) {
                    Text("Tutup")
                }
            },

            title = {
                Text("Riwayat Task", fontWeight = FontWeight.Bold)
            },

            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(300.dp)
                ) {
                    items(history) { item ->

                        val parts = item.split("|")

                        val title = parts.getOrNull(0)?.trim() ?: "-"
                        val exp = parts.getOrNull(1)?.trim() ?: "-"
                        val date = parts.getOrNull(2)?.trim() ?: "-"

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(Color(0xFFEDE7F6))
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                Text(title, fontWeight = FontWeight.Bold)
                                Text("Tanggal: $date")
                                Text("Reward: $exp")
                            }
                        }
                    }
                }
            }
        )
    }
}