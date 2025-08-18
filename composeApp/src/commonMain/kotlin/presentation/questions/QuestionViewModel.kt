package presentation.questions

import base.BaseVM
import base.State
import cafe.adriel.voyager.core.model.screenModelScope
import domain.GameRepository
import kotlinx.coroutines.launch
import models.GameCard
import presentation.questions.model.QuestionInitData

data class QuestionState(
    val games: List<GameCard> = emptyList(),
    val title: String = "",
    val colorHex: String = "#000000",
) : State

class QuestionViewModel(
    private val repository: GameRepository
) : BaseVM<QuestionState, QuestionInitData>(QuestionState()) {
    override fun initWithData(initData: QuestionInitData) {
        super.initWithData(initData)
        updateState {
            copy(
                title = initData.title,
                colorHex = initData.colorHex
            )
        }
        screenModelScope.launch {
            val games = repository.getGamesForPack(initData.id)
            println("QuestionViewModel ${initData.id} ${games}")
            updateState {
                copy(
                    games = games
                )
            }
        }
    }
}