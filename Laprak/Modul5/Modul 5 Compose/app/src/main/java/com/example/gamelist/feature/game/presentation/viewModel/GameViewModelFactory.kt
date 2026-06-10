package com.example.gamelist.feature.game.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gamelist.core.database.AppDatabase
import com.example.gamelist.core.network.NetworkModule
import com.example.gamelist.core.preferences.AppPreferences
import com.example.gamelist.feature.game.data.remote.GameApiService
import com.example.gamelist.feature.game.data.repository.GameRepositoryImpl

class GameViewModelFactory(
    private val title: String,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = NetworkModule.createService<GameApiService>()
        val appDatabase = AppDatabase.getDatabase(context)
        val repository = GameRepositoryImpl(apiService, appDatabase.gameDao())
        val appPreferences = AppPreferences(context)
        return GameViewModel(title, repository, appPreferences) as T
    }
}