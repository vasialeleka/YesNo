import androidx.compose.ui.window.ComposeUIViewController
import base.baseModule
import base.initKoin
import presentation.main.mainCardsModule

fun MainViewController() = ComposeUIViewController {
    initKoin(baseModule, mainCardsModule)
    App()
}