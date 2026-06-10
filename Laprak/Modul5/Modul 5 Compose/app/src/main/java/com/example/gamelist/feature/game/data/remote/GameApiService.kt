package com.example.gamelist.feature.game.data.remote

import com.example.gamelist.feature.game.data.remote.dto.GameResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 10
    ): GameResponseDto

    @GET("games")
    suspend fun searchGame(
        @Query("key") apiKey: String,
        @Query("search") query: String,
        @Query("page_size") pageSize: Int = 1
    ): GameResponseDto
}