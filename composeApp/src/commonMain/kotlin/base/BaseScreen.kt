package base

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

abstract class BaseScreen<VM : BaseVM<S, D>, S: State, D: InitData?> : Screen {

    @Composable
    abstract fun provideVm(): VM

    open fun provideInitData(): D? = null //можна видалити

    @Composable
    override fun Content() {
        val viewModel = provideVm()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            provideInitData()?.let {
                viewModel.initWithData(it)
            }
        }

        Content(state = state, viewModel = viewModel, navigator = navigator)
    }

    @Composable
    protected abstract fun Content(
        state: S,
        viewModel: VM,
        navigator: Navigator
    )
}
