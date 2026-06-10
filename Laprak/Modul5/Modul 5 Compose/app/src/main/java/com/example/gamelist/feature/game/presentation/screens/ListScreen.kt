package com.example.gamelist.feature.game.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamelist.feature.game.domain.model.Game
import com.example.gamelist.feature.game.presentation.components.GameItem

@Composable
fun ListScreen(
    navController: NavController,
    list: List<Game>,
    onDetailClick: (Game) -> Unit
) {

    val context = LocalContext.current

    if (list.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF6200EE))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA)),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {

            items(
                items = list,
                key = { it.name }
            ) { game ->

                GameItem(
                    game = game,
                    navController = navController,
                    context = context,
                    onDetailClick = onDetailClick
                )
            }
        }
    }
}