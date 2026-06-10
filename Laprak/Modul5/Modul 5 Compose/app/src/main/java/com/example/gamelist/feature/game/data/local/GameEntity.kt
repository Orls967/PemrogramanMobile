package com.example.gamelist.feature.game.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val year: String,
    val desc: String,
    val genre: String,
    val imageUrl: String?,
    val image: Int,
    val url: String
)