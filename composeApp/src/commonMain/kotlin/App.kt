import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import data.FirestoreGameRepository
import presentation.UserScreen

@Composable
fun App() {
    val userRepository = remember { FirestoreGameRepository() }
    UserScreen(userRepository)
}
