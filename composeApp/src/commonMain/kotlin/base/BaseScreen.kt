package base

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

abstract class BaseScreen<VM : BaseVM<S>, S: State> : Screen {

    @Composable
    abstract fun provideVm() : VM

    @Composable
    override fun Content() {
        val viewModel = provideVm()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Content(state = state, viewModel = viewModel, navigator = navigator)
    }

    @Composable
    protected abstract fun Content(
        state: S,
        viewModel: VM,
        navigator: cafe.adriel.voyager.navigator.Navigator)
}
