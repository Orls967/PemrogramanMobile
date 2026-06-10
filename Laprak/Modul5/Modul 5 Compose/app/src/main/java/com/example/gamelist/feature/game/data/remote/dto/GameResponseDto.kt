package com.example.gamelist.feature.game.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameResponseDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<GameDto>
)