import androidx.compose.ui.window.ComposeUIViewController
import base.baseModule
import base.initKoin
import presentation.description.descriptionModule
import presentation.main.mainCardsModule
import presentation.questions.questionModule

fun MainViewController() = ComposeUIViewController {
    initKoin(baseModule, mainCardsModule, descriptionModule, questionModule)
    App()
}