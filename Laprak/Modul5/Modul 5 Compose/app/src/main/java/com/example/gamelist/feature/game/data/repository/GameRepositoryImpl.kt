package com.example.gamelist.feature.game.data.repository

import com.example.gamelist.BuildConfig
import com.example.gamelist.R
import com.example.gamelist.core.network.ApiResult
import com.example.gamelist.core.network.safeApiCall
import com.example.gamelist.feature.game.data.local.GameDao
import com.example.gamelist.feature.game.data.local.GameEntity
import com.example.gamelist.feature.game.data.remote.GameApiService
import com.example.gamelist.feature.game.domain.model.Game
import com.example.gamelist.feature.game.domain.repository.GameRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GameRepositoryImpl(
    private val apiService: GameApiService,
    private val gameDao: GameDao
) : GameRepository {

    override fun getGames(): Flow<ApiResult<List<Game>>> = flow {
        emit(ApiResult.Loading)

        val gameNames = listOf(
            "Mobile Legends",
            "Cyberpunk 2077",
            "Genshin Impact",
            "Valorant",
            "Dota 2",
            "The Last of Us Part II",
            "Resident Evil 9",
            "Grand Theft Auto V",
            "Ghost of Tsushima",
            "Clair Obscur: Expedition 33"
        )

        val result = safeApiCall {
            coroutineScope {
                gameNames.map { name ->
                    async {
                        val response = apiService.searchGame(BuildConfig.RAWG_API_KEY, name)
                        response.results.firstOrNull()
                    }
                }.mapNotNull { it.await() }
            }
        }

        when (result) {
            is ApiResult.Success -> {
                try {
                    val entities = result.data.map { dto ->
                        val genresStr = dto.genres?.joinToString { it.name } ?: "Unknown"
                        val releasedDate = dto.released ?: "unknown date"
                        val ratingValue = dto.rating ?: 0.0
                        val dynamicDesc = "Explore ${dto.name}, a game of genre $genresStr. Released on $releasedDate with a rating of $ratingValue/5."

                        GameEntity(
                            name = dto.name,
                            year = dto.released?.take(4) ?: "N/A",
                            desc = dynamicDesc,
                            genre = dto.genres?.firstOrNull()?.name ?: "Unknown",
                            imageUrl = dto.backgroundImage,
                            image = R.drawable.ic_launcher_foreground,
                            url = "https://rawg.io/games/${dto.slug ?: ""}"
                        )
                    }

                    gameDao.clearGames()
                    gameDao.insertGames(entities)

                    val localEntities = gameDao.getAllGames().first()
                    val domainGames = localEntities.map { entity ->
                        Game(
                            name = entity.name,
                            year = entity.year,
                            desc = entity.desc,
                            genre = entity.genre,
                            image = entity.image,
                            url = entity.url,
                            imageUrl = entity.imageUrl
                        )
                    }
                    emit(ApiResult.Success(domainGames))
                } catch (e: Exception) {
                    emit(ApiResult.Error(e, "Database cache writing error: ${e.message}"))
                }
            }
            is ApiResult.Error -> {
                try {
                    val localEntities = gameDao.getAllGames().first()
                    if (localEntities.isNotEmpty()) {
                        val domainGames = localEntities.map { entity ->
                            Game(
                                name = entity.name,
                                year = entity.year,
                                desc = entity.desc,
                                genre = entity.genre,
                                image = entity.image,
                                url = entity.url,
                                imageUrl = entity.imageUrl
                            )
                        }
                        emit(ApiResult.Success(domainGames))
                    } else {
                        emit(ApiResult.Error(result.exception, result.message))
                    }
                } catch (e: Exception) {
                    emit(ApiResult.Error(result.exception, "Offline cache loading failed: ${result.message}"))
                }
            }
            is ApiResult.Loading -> {
                // Emitted at start
            }
        }
    }
}