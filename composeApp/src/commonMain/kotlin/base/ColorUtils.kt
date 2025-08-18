package base

import androidx.compose.ui.graphics.Color

fun fromHex(hex: String): Color {
    val cleanedHex = hex.removePrefix("#")
    val argb = 0xFF000000 or cleanedHex.toLong(16)
    return Color(argb)
}