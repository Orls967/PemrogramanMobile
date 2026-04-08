package com.example.aplikasitugaskelompok

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyApp(this) }
    }
}

@Composable
fun MyApp(context: Context) {
    val navController = rememberNavController()
    val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    NavHost(
        navController,
        startDestination = if (prefs.getBoolean("isLoggedIn", false)) "home" else "login"
    ) {
        composable("login") { LoginScreen(navController, context) }
        composable("home") { HomeScreen(navController, context) }
        composable("calendar") { CalendarScreen(navController, context) }
    }
}

// ================= LOGIN =================

@Composable
fun LoginScreen(navController: NavController, context: Context) {
    val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(24.dp), Arrangement.Center) {
        Text("Login", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        OutlinedTextField(username, { username = it }, label = { Text("Username") })
        OutlinedTextField(password, { password = it }, label = { Text("Password") })

        Spacer(Modifier.height(12.dp))

        Button(onClick = {
            prefs.edit().putBoolean("isLoggedIn", true).apply()
            navController.navigate("home") { popUpTo("login") { inclusive = true } }
        }) { Text("Login") }
    }
}

// ================= DATA =================

data class Task(
    val title: String,
    val difficulty: String,
    val createdDate: String,
    val exp: Int
)

// ================= HOME =================

@Composable
fun HomeScreen(navController: NavController, context: Context) {

    val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    var exp by remember { mutableStateOf(prefs.getInt("exp", 60)) }
    var level by remember { mutableStateOf(prefs.getInt("level", 5)) }
    var streak by remember { mutableStateOf(6) }

    var showLevelUp by remember { mutableStateOf(false) }
    var showTaskDone by remember { mutableStateOf(false) }
    var lastExpGained by remember { mutableStateOf(0) }

    // LOAD TASK
    var tasks by remember {
        mutableStateOf(
            prefs.getStringSet("tasks", setOf())!!.map {
                val p = it.split("|")
                Task(p[0], p[1], p[2], p[3].toInt())
            }
        )
    }

    var history by remember {
        mutableStateOf(
            listOf(
                "Belajar Kotlin | +50 XP | 2026-04-01 → 2026-04-01",
                "Workout | +25 XP | 2026-04-02 → 2026-04-02",
                "Baca Buku | +150 XP | 2026-04-03 → 2026-04-03"
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var showHistory by remember { mutableStateOf(false) }

    fun saveTasks() {
        val set = tasks.map {
            "${it.title}|${it.difficulty}|${it.createdDate}|${it.exp}"
        }.toSet()
        prefs.edit().putStringSet("tasks", set).apply()
    }

    fun saveData() {
        prefs.edit()
            .putInt("exp", exp)
            .putInt("level", level)
            .apply()
    }

    // 🔥 FIX LEVEL SYSTEM BENAR (MULTI LEVEL)
    fun checkLevelUp() {
        var leveled = false
        while (exp >= 100) {
            exp -= 100
            level++
            leveled = true
        }
        if (leveled) showLevelUp = true
        saveData()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp) // 🔥 lebih lega
                ) {

                    // 🔥 HEADER
                    Text(
                        text = "Progresi",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    // ================= LEVEL CARD =================
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Text(
                                text = "Level $level",
                                fontWeight = FontWeight.Bold
                            )

                            LinearProgressIndicator(
                                progress = exp / 100f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp) // 🔥 lebih tebal & clean
                            )

                            Text("EXP: $exp / 100")
                        }
                    }

                    // ================= STREAK CARD =================
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = "🔥 Streak: $streak hari",
                                fontWeight = FontWeight.Bold
                            )

                            Text("1 hari lagi menuju reward 🎁")

                        }
                    }

                    // ================= TASK AKTIF TITLE =================
                    if (tasks.isNotEmpty()) {
                        Text(
                            text = "Task Aktif",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            items(tasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(task.title, fontWeight = FontWeight.Bold)
                        Text("${task.difficulty} (+${task.exp} XP)")

                        Spacer(Modifier.height(8.dp))

                        Button(onClick = {
                            lastExpGained = task.exp
                            exp += task.exp

                            showTaskDone = true
                            checkLevelUp()

                            tasks = tasks - task
                            saveTasks()

                            history = history + "${task.title} | +${task.exp} XP"

                        }) {
                            Text("Task Selesai")
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally, // 🔥 center semua
                    verticalArrangement = Arrangement.spacedBy(16.dp) // 🔥 jarak lebih lega
                ) {

                    val buttonModifier = Modifier
                        .fillMaxWidth(0.8f) // 🔥 semua tombol sama lebar (80% layar)
                        .height(55.dp) // 🔥 tinggi konsisten (biar clean)

                    Button(
                        onClick = { showDialog = true },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(30.dp) // 🔥 rounded modern
                    ) {
                        Text("📜 Buat Task")
                    }

                    Button(
                        onClick = { navController.navigate("calendar") },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("📅 Lihat Streak Calendar")
                    }

                    Button(
                        onClick = { showHistory = true },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text("📜 Riwayat Task")
                    }
                }
            }
        }

        Button(
            onClick = {
                prefs.edit().clear().apply()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text("🚪 Sign Out")
        }
    }

    // POPUP
    if (showTaskDone) {
        AlertDialog(
            onDismissRequest = { showTaskDone = false },
            confirmButton = { Button({ showTaskDone = false }) { Text("OK") } },
            title = { Text("✅ Task Selesai!") },
            text = { Text("XP bertambah +$lastExpGained") }
        )
    }

    if (showLevelUp) {
        AlertDialog(
            onDismissRequest = { showLevelUp = false },
            confirmButton = { Button({ showLevelUp = false }) { Text("Mantap!") } },
            title = { Text("🎉 LEVEL UP!") },
            text = { Text("Kamu naik ke level $level 🚀") }
        )
    }

    if (showHistory) {
        AlertDialog(
            onDismissRequest = { showHistory = false },
            confirmButton = {
                Button(
                    onClick = { showHistory = false },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Tutup")
                }
            },
            title = {
                Text(
                    "Riwayat Task",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.padding(top = 8.dp) // 🔥 INI YANG KAMU TANYA
                ) {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(history) { item ->

                            val parts = item.split("|")

                            val title = parts.getOrNull(0) ?: "-"
                            val exp = parts.getOrNull(1)?.trim() ?: "0 XP"
                            val dates = parts.getOrNull(2)?.split("→") ?: listOf("-", "-")

                            val startDate = dates.getOrNull(0)?.trim() ?: "-"
                            val endDate = dates.getOrNull(1)?.trim() ?: "-"

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {

                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text("Task dimulai : $startDate")
                                    Text("Task selesai : $endDate")

                                    Text(
                                        text = "Exp : $exp",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    if (showDialog) {
        var title by remember { mutableStateOf("") }
        var difficulty by remember { mutableStateOf("Easy") }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    val expValue = when (difficulty) {
                        "Easy" -> 25
                        "Medium" -> 50
                        else -> 150
                    }
                    tasks = tasks + Task(title, difficulty, LocalDate.now().toString(), expValue)
                    saveTasks()
                    showDialog = false
                }) { Text("Simpan") }
            },
            dismissButton = { Button({ showDialog = false }) { Text("Batal") } },
            title = { Text("Buat Task") },
            text = {
                Column {
                    OutlinedTextField(title, { title = it }, label = { Text("Nama Task") })
                    DropdownMenuDemo(difficulty) { difficulty = it }
                }
            }
        )
    }
}

// ================= DROPDOWN =================

@Composable
fun DropdownMenuDemo(selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val options = listOf(
        "Easy (+25 XP)",
        "Medium (+50 XP)",
        "Hard (+150 XP)"
    )

    Column {
        Button(onClick = { expanded = true }) {
            Text(selected)
        }

        DropdownMenu(expanded, { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it.substringBefore(" "))
                        expanded = false
                    }
                )
            }
        }
    }
}

// ================= CALENDAR =================
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
            contentPadding = PaddingValues(bottom = 80.dp), // 🔥 FIX SCROLL
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    "Calendar 🔥",
                    style = MaterialTheme.typography.headlineMedium, // 🔥 gedein font
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Text(
                            text = "🔥 Streak sekarang: 6 hari",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        LinearProgressIndicator(
                            progress = { 6f / 7f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )

                        Text(
                            text = "1 hari lagi menuju reward: 250 XP",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Next reward: 30 hari → Peci Premium",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = "⚠️ Streak akan reset jika tidak menyelesaikan task hari ini",
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                }
            }

            // 🔥 GRID CALENDAR
            items(weeks) { week ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    week.forEach { day ->

                        if (day == 0) {
                            Spacer(modifier = Modifier.size(50.dp)) // 🔥 biar sejajar
                        } else {

                            val isToday = day == today
                            Card(
                                modifier = Modifier.size(50.dp),
                                shape = RoundedCornerShape(12.dp),

                                // ✅ TAMBAH INI
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = if (day in streakDays) 8.dp else 2.dp
                                ),

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
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyLarge // 🔥 font lebih besar
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text(
                    "🎁 Reward Streak",
                    style = MaterialTheme.typography.titleLarge, // 🔥 samain sama home
                    fontWeight = FontWeight.Bold
                )
            }

            item { RewardCard("7 hari streak", "250 XP") }
            item { RewardCard("30 hari streak", "Peci Premium") }
            item { RewardCard("100 hari streak", "Motor Vario 160") }
            item { RewardCard("180 hari streak", "Laptop Gaming") }
            item { RewardCard("365 hari streak", "Liburan Bali") }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Kembali")
        }
    }
}

@Composable
fun RewardCard(title: String, reward: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text("Reward: $reward")
        }
    }
}