package presentation.main

import base.BaseVM
import base.EmptyInitData
import base.State
import domain.GameRepository
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.GameCardPack

data class MainCardsState(
    val packs: List<GameCardPack> = emptyList()
) : State

class MainCardsViewModel(
    private val repository: GameRepository
) : BaseVM<MainCardsState, EmptyInitData>(MainCardsState()) {
    init {
        screenModelScope.launch {
            val result = repository.getGamePacks()
            println("MainCardsViewModel ${result}")
            updateState {
                copy(packs = result)
            }
        }
    }
}