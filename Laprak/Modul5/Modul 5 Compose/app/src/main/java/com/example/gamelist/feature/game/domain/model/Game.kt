package com.example.gamelist.feature.game.domain.model

data class Game(
    val name: String,
    val year: String,
    val desc: String,
    val genre: String,
    val image: Int,
    val url: String,
    val imageUrl: String? = null
)