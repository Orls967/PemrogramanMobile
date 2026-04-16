package com.example.myapplication

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.myapplication.screens.*

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    NavHost(
        navController = navController,
        startDestination = if (prefs.getBoolean("isLoggedIn", false)) "home" else "login"
    ) {
        composable("login") { LoginScreen(navController, context) }
        composable("home") { HomeScreen(navController, context) }
        composable("calendar") { CalendarScreen(navController, context) }
    }
}