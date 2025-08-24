package presentation.description

import base.BaseVM
import base.State
import billing.BillingRepository
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import presentation.description.model.DescriptionInitData

data class DescriptionState(
    val title: String = "",
    val colorHex: String = "#000000",
    val cardDescription: List<String> = emptyList()
) : State

class DescriptionViewModel(
    private val billingRepository: BillingRepository
) : BaseVM<DescriptionState, DescriptionInitData>(DescriptionState()) {
    override fun initWithData(initData: DescriptionInitData) {
        super.initWithData(initData)
        updateState {
            copy(
                title = initData.title,
                cardDescription = initData.description,
                colorHex = initData.colorHex
            )
        }
        screenModelScope.launch {
            println("Result ${billingRepository.getPurchasedProducts()}")
            val result = billingRepository.launchPurchaseFlow("about_kills")
            println("Result ${result}")

        }
    }
}