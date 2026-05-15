package com.example.gamelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import timber.log.Timber

@Composable
fun GameListApp() {

    val navController = rememberNavController()

    val viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory("Game List Application")
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