package presentation.main

import base.BaseVM
import base.State
import domain.GameRepository
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

data class MainCardsState(
    val result : String
) : State

class MainCardsViewModel(
    private val repository: GameRepository
) : BaseVM<MainCardsState>(MainCardsState("")) {
    init {
        screenModelScope.launch {
            val result = repository.getGamePacks()
            println("MainCardsViewModel ${result}")
            updateState {
                copy(result = result.toString())
            }
        }
    }
}