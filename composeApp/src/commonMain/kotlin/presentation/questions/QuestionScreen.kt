package presentation.questions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class QuestionScreen : Screen {
    @Composable
    override fun Content() {
        Text(text = "Questions")
    }
}