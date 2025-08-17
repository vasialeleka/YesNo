import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import data.FirestoreGameRepository
import presentation.UserScreen
import presentation.main.MainCardsScreen

@Composable
fun App() {

    val userRepository = remember { FirestoreGameRepository() }
    Navigator(
        screen = MainCardsScreen()
    )
}

