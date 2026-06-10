package com.example.gamelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gamelist.app.navigation.GameListApp
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        Timber.d("MainActivity berhasil dijalankan")

        setContent {
            GameListApp()
        }
    }
}