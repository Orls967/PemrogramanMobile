package com.example.gamelist.feature.game.domain.repository

import com.example.gamelist.core.network.ApiResult
import com.example.gamelist.feature.game.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<ApiResult<List<Game>>>
}