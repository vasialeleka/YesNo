package models

data class GameCardPack(
    val id: String,
    val title: Map<String, String>,
    val description: Map<String, List<String>>,
    val productId: String,
    val isPaid: Boolean,
    val priceAmount: Double = 0.0,
    val colorHex: String,
) {
    fun ifYouHadToPay() = isPaid || priceAmount == 0.0
    fun getTitle(language: String = "en") = title[language] ?: title["en"] ?: ""
    fun getDescription(language: String = "en") = description[language] ?: description["en"] ?: emptyList()
    fun getPrice(isPaid:Boolean) : String {
        return when{
            isPaid -> "Free"
            priceAmount == 0.0 -> "Free"
            else ->  "Buy or subscribe"
        }
    }
}

data class GameCard(
    val id: String,
    val question: Map<String, String>,
    val answer: Map<String, String>,
    val title: Map<String, String>
) {
    fun getQuestion(language: String ="en") = question[language] ?: question["en"] ?: ""
    fun getAnswer(language: String="en") = answer[language] ?: answer["en"] ?: ""
    fun getTitle(language: String="en") = title[language] ?: title["en"] ?: ""
}