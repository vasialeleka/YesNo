package presentation.main

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import base.BaseScreen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.description.DescriptionScreen

class MainCardsScreen : BaseScreen<MainCardsViewModel, MainCardsState>(){

    @Composable
    override fun provideVm() = koinScreenModel<MainCardsViewModel>()

    @Composable
    override fun Content(
        state: MainCardsState,
        viewModel: MainCardsViewModel,
        navigator: Navigator
    ) {
        Text(text = "State: ${state.result}")

        val navigator = LocalNavigator.currentOrThrow
        Text("Main Cards ${state.result}")
        Button(onClick = {
            navigator.push(DescriptionScreen())
        }) {
            Text("Click to details")
        }
    }
}