package presentation.description

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class DescriptionScreen : Screen {
    @Composable
    override fun Content() {
        Text("Description Cards")
    }
}