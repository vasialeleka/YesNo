package presentation.description

import base.BaseVM
import base.State
import presentation.description.model.DescriptionInitData

data class DescriptionState(
    val title: String = "",
    val colorHex: String = "",
    val cardDescription: List<String> = emptyList()
) : State

class DescriptionViewModel : BaseVM<DescriptionState, DescriptionInitData>(DescriptionState()) {
    override fun initWithData(initData: DescriptionInitData) {
        super.initWithData(initData)
        updateState {
            copy(
                title = initData.title,
                cardDescription = initData.description,
                colorHex = initData.colorHex
            )
        }
    }
}