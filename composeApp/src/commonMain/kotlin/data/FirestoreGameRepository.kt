package data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import domain.GameRepository
import models.GameCard
import models.GameCardPack

class FirestoreGameRepository : GameRepository {

    private val firestore = Firebase.firestore

    override suspend fun getGamePacks(): List<GameCardPack> {
        return firestore.collection("LanguageGamesCards")
            .get()
            .documents
            .mapNotNull { it.toGameCardPack() } // без ігор
    }

    override suspend fun getGamesForPack(id: String): List<GameCard> {
        return firestore.collection("LanguageGamesCards")
            .document(id)
            .collection("game") // <- games мають бути підколекцією
            .get()
            .documents
            .mapNotNull { it.toGameCard() }
            .sortedBy { it.id.toIntOrNull() ?: Int.MAX_VALUE }
    }
}

fun DocumentSnapshot.toGameCardPack(): GameCardPack? {
    return try {

        GameCardPack(
            id = id,
            title = get("title") as? Map<String, String> ?: emptyMap(),
            description = get("description") as? Map<String, List<String>> ?: emptyMap() ,
            productId = get("productId") ?: "",
            isPaid = get("isPaid")?: false,
            priceAmount = get("priceAmount") ?: 0.0,
            colorHex = get("colorHex") ?: "#000000"
        )
    } catch (e: Exception) {
        println("🔥 Failed to fetch GameCardPack: $e")
        null
    }
}

fun DocumentSnapshot.toGameCard(): GameCard? {
    return try {
        GameCard(
            id = id,
            answer = get("answer") as? Map<String, String> ?: emptyMap(),
            question = get("question") as? Map<String, String> ?: emptyMap(),
            title = get("title") as? Map<String, String> ?: emptyMap()
        )
    } catch (e: Exception) {
        println("🔥 Failed to fetch GamePack: $e")
        null
    }
}