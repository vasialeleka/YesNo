package billing

expect class BillingRepository {

    /** Повертає поточний список куплених продуктів */
    suspend fun getPurchasedProducts(): List<PurchasedProduct>

    /** Запускає флоу покупки */
    suspend fun launchPurchaseFlow(productId: String): PurchaseResult
}

data class PurchasedProduct(
    val productId: String,
    val purchaseToken: String,
    val isAcknowledged: Boolean
)

sealed class PurchaseResult {
    data class Success(val product: PurchasedProduct) : PurchaseResult()
    object Cancelled : PurchaseResult()
    data class Error(val message: String) : PurchaseResult()
}