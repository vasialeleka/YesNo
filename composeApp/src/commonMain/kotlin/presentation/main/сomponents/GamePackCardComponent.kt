package presentation.main.сomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.fromHex
import models.GameCardPack

@Composable
fun GamePackCardComponent(
    game: GameCardPack, onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLast: Boolean = false,
    isPaid: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(
                if (isLast) RoundedCornerShape(48.dp) else RoundedCornerShape(
                    topEnd = 48.dp,
                    topStart = 48.dp
                )
            )
            .background(fromHex(game.colorHex))
            .clickable { onClick() }

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 12.dp, start = 24.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f), text = game.getTitle(), style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )
                )

                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = if (isLast) 12.dp else 52.dp,
                        start = 24.dp,
                        end = 24.dp
                    )
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.getPrice(isPaid),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black, // Колір тексту
                        textAlign = TextAlign.Center // Текст вирівняний по центру
                    )
                )
            }
        }
    }
}