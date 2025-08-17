package domain

import models.GameCardPack

interface GameRepository {
    suspend fun getGamePacks(): List<GameCardPack>
    suspend  fun getGamePackById(id: String): GameCardPack?}

