package com.example.gamelist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun GameListApp() {

    val navController = rememberNavController()

    val list = listOf(

        Game(
            "Mobile Legends",
            "2016",
            "Mobile Legends adalah game MOBA 5v5 yang sangat populer di perangkat mobile. Dalam permainan ini, pemain bekerja sama dalam tim untuk menghancurkan base lawan dengan strategi, koordinasi, dan pemilihan hero yang tepat. Setiap pemain memiliki peran seperti tank, marksman, mage, dan assassin yang harus dimainkan secara optimal.",
            "MOBA",
            R.drawable.ml,
            "https://www.mobilelegends.com"
        ),

        Game(
            "Genshin Impact",
            "2020",
            "Genshin Impact adalah game RPG open-world dengan grafis bergaya anime yang sangat detail dan indah. Pemain dapat menjelajahi dunia Teyvat yang luas, menyelesaikan berbagai quest, serta bertarung menggunakan sistem elemen yang unik. Game ini juga memiliki sistem gacha untuk mendapatkan karakter dan senjata.",
            "RPG",
            R.drawable.genshin,
            "https://genshin.hoyoverse.com"
        ),

        Game(
            "Resident Evil 9",
            "2025",
            "Resident Evil 9 menghadirkan pengalaman survival horror dengan atmosfer yang mencekam dan penuh ketegangan. Game ini menampilkan cerita yang gelap, lingkungan yang detail, serta gameplay yang menantang di mana pemain harus bertahan hidup dari ancaman zombie dan makhluk berbahaya lainnya.",
            "Horror",
            R.drawable.requiem,
            "https://www.capcom.com"
        ),

        Game(
            "PUBG Mobile",
            "2018",
            "PUBG Mobile adalah game battle royale di mana hingga 100 pemain bertarung dalam satu peta untuk menjadi yang terakhir bertahan hidup.",
            "Battle Royale",
            R.drawable.pubg,
            "https://www.pubgmobile.com"
        ),

        Game(
            "Valorant",
            "2020",
            "Valorant adalah game FPS taktis berbasis tim yang menggabungkan mekanik shooter klasik dengan kemampuan unik setiap karakter.",
            "FPS",
            R.drawable.valorant,
            "https://playvalorant.com"
        ),

        Game(
            "Dota 2",
            "2013",
            "Dota 2 adalah game MOBA kompetitif di platform PC yang dikenal dengan kompleksitas gameplay dan strategi mendalam.",
            "MOBA",
            R.drawable.dota2,
            "https://www.dota2.com"
        ),

        Game(
            "Elden Ring",
            "2022",
            "Elden Ring adalah game RPG open-world bergaya souls-like yang menawarkan dunia luas dengan kebebasan eksplorasi tinggi.",
            "RPG",
            R.drawable.eldenring,
            "https://en.bandainamcoent.eu/elden-ring"
        ),

        Game(
            "Gran Turismo 7",
            "2022",
            "Gran Turismo 7 adalah game simulasi balap realistis yang menghadirkan pengalaman berkendara autentik.",
            "Racing",
            R.drawable.granturismo7,
            "https://www.gran-turismo.com"
        ),

        Game(
            "GTA V",
            "2013",
            "GTA V adalah game open-world yang memberikan kebebasan kepada pemain untuk menjelajahi kota Los Santos.",
            "Action",
            R.drawable.gtav,
            "https://www.rockstargames.com/gta-v"
        ),

        Game(
            "Survivor.io",
            "2022",
            "Survivor.io adalah game survival casual di mana pemain harus bertahan dari serangan zombie yang terus berdatangan.",
            "Casual",
            R.drawable.survivorio,
            "https://store.habby.com/game/3"
        )
    )

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            ListScreen(navController, list)
        }

        composable("detail/{name}") { backStackEntry ->

            val encodedName = backStackEntry.arguments?.getString("name") ?: ""
            val decodedName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())

            val game = list.find { it.name.equals(decodedName, ignoreCase = true) }

            if (game != null) {
                DetailScreen(
                    game = game,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}