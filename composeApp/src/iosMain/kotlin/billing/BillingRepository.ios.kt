package billing

actual class BillingRepository {
    actual suspend fun getPurchasedProducts(): List<PurchasedProduct> {
        TODO("Not yet implemented")
    }

    actual suspend fun launchPurchaseFlow(productId: String): PurchaseResult {
        TODO("Not yet implemented")
    }
}