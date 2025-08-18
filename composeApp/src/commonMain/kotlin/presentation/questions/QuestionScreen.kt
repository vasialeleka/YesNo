package presentation.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.BaseScreen
import base.fromHex
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import models.GameCard
import org.koin.core.parameter.parametersOf
import presentation.questions.model.QuestionInitData

class QuestionScreen(
    private val initData: QuestionInitData
) : BaseScreen<QuestionViewModel,QuestionState, QuestionInitData>() {
    override fun provideInitData() = initData
    @Composable
    override fun provideVm() = koinScreenModel<QuestionViewModel>(
        parameters = { parametersOf(initData) }
    )

    @Composable
    override fun Content(
        state: QuestionState,
        viewModel: QuestionViewModel,
        navigator: Navigator
    ) {
       GameScreenContent(
           games = state.games,
           title = state.title,
           colorHex = state.colorHex
       ) {
           navigator.pop()
       }
    }

}

@Composable
fun GameScreenContent(
    games: List<GameCard>,
    title: String,
    colorHex: String,
    onBack: () -> Unit
) {
    var currentIndex = remember { mutableIntStateOf(0) }
    var showAnswer = remember { mutableStateOf(false) }

    val currentGame = games.getOrNull(currentIndex.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fromHex(colorHex))
            .padding(16.dp)
    ) {
        // 🔹 Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Назад")
            }

            Text(
                text = title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // 🔹 Основна область з питанням/відповіддю
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.3f))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (!currentGame?.getTitle().isNullOrEmpty()) {
                    Text(
                        text = currentGame?.getTitle() ?: "",
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                    )
                }

                Text(
                    text = if (showAnswer.value) currentGame?.getAnswer() ?: "" else currentGame?.getQuestion() ?: "",
                    style = TextStyle(fontSize = 18.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // 🔹 Кнопки
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.6f))
                    .clickable { showAnswer.value = !showAnswer.value }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (showAnswer.value) "Сховати відповідь" else "Показати відповідь",
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomOutlinedButton(
                    text = "Назад",
                    enabled = currentIndex.value > 0,
                    modifier = Modifier.weight(1f)
                ) {
                    currentIndex.value--
                    showAnswer.value = false
                }

                CustomOutlinedButton(
                    text = "Далі",
                    enabled = currentIndex.value < games.lastIndex,
                    modifier = Modifier.weight(1f)
                ) {
                    currentIndex.value++
                    showAnswer.value = false
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(
                if (enabled) Color.White.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(50))
            .clickable(enabled = enabled) { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )
    }
}