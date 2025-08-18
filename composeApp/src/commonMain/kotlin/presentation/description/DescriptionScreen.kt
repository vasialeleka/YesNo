package presentation.description

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import base.BaseScreen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.parameter.parametersOf
import presentation.description.model.DescriptionInitData

class DescriptionScreen(
    private val initData: DescriptionInitData
) : BaseScreen<DescriptionViewModel, DescriptionState, DescriptionInitData>() {
    override fun provideInitData() = initData

    @Composable
    override fun provideVm(): DescriptionViewModel = koinScreenModel<DescriptionViewModel>(
        parameters = { parametersOf(initData) }
    )

    @Composable
    override fun Content(
        state: DescriptionState,
        viewModel: DescriptionViewModel,
        navigator: Navigator
    ) {
        Text(state.title)
    }
}