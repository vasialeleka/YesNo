package domain

import models.GameCard
import models.GameCardPack

interface GameRepository {
    suspend fun getGamePacks(): List<GameCardPack>
    suspend fun getGamesForPack(id: String): List<GameCard>
}

