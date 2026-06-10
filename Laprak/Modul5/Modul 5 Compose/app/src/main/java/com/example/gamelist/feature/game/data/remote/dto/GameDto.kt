package com.example.gamelist.feature.game.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: Int,
    val name: String,
    val released: String? = null,
    @SerialName("background_image") val backgroundImage: String? = null,
    val rating: Double? = null,
    val slug: String? = null,
    val genres: List<GenreDto>? = null
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String,
    val slug: String
)