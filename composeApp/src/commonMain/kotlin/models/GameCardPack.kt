package models

data class GameCardPack(
    val id: String,
    val title: Map<String, String>,
    val description: Map<String, String>,
    val productId: String,
    val isPaid: Boolean,
    val priceAmount: Int,
    val colorHex: String,
)

data class GameCard(
    val id: String,
    val question: Map<String, String>,
    val answer: Map<String, String>,
    val title: Map<String, String>
)