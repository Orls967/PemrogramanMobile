package com.example.gamelist.feature.game.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamelist.R
import com.example.gamelist.core.network.ApiResult
import com.example.gamelist.core.preferences.AppPreferences
import com.example.gamelist.feature.game.domain.model.Game
import com.example.gamelist.feature.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class GameViewModel(
    val title: String,
    private val repository: GameRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _listGame = MutableStateFlow<List<Game>>(emptyList())

    val listGame: StateFlow<List<Game>> = _listGame

    private val _selectedGame =
        MutableStateFlow<Game?>(null)

    val selectedGame: StateFlow<Game?> =
        _selectedGame

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            repository.getGames().collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        Timber.d("Loading games from remote API...")
                    }
                    is ApiResult.Success -> {
                        Timber.d("Successfully loaded games from remote API")
                        _listGame.value = result.data
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load games from API: ${result.message}")
                    }
                }
            }
        }
    }

    fun selectGame(game: Game) {
        _selectedGame.value = game
        appPreferences.saveLastOpenedGame(game.name)
        Timber.d("Game selected and saved to SharedPreferences: ${game.name}")
    }
}