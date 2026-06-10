package com.example.gamelist.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.gamelist.feature.game.presentation.viewModel.GameViewModel
import com.example.gamelist.feature.game.presentation.viewModel.GameViewModelFactory
import com.example.gamelist.feature.game.presentation.screens.ListScreen
import com.example.gamelist.feature.game.presentation.screens.DetailScreen
import timber.log.Timber

@Composable
fun GameListApp() {

    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    val viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory("Game List Application", context)
    )

    val list by viewModel.listGame.collectAsState()

    val selectedGame by viewModel.selectedGame.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {

            ListScreen(
                navController = navController,
                list = list,
                onDetailClick = { game ->

                    Timber.d(
                        "Berpindah ke halaman detail game: ${game.name}"
                    )

                    viewModel.selectGame(game)

                    navController.navigate("detail")
                }
            )
        }

        composable("detail") {

            selectedGame?.let { game ->

                DetailScreen(
                    game = game,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}