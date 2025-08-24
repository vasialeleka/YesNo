package billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryPurchasesAsync
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.dsl.module
import kotlin.coroutines.resume


fun billingModule(activity: Activity) = module {

    single { BillingRepository(activity) }
}

actual class BillingRepository (
    private val activity: Activity
) : PurchasesUpdatedListener {

    private val billingClient: BillingClient = BillingClient.newBuilder(activity.baseContext)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    private var purchaseCallback: ((PurchaseResult) -> Unit)? = null

    init {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {}
            override fun onBillingServiceDisconnected() {}
        })
    }

    actual suspend fun getPurchasedProducts(): List<PurchasedProduct> {
        val result = billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        return result.purchasesList.map {
            PurchasedProduct(
                productId = it.products.first(),
                purchaseToken = it.purchaseToken,
                isAcknowledged = it.isAcknowledged
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual suspend fun launchPurchaseFlow(productId: String): PurchaseResult {
        val queryParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            )
            .build()

        // Отримуємо список доступних продуктів
        val productDetailsList = suspendCancellableCoroutine<List<ProductDetails>> { continuation ->
            billingClient.queryProductDetailsAsync(queryParams) { billingResult, products ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    continuation.resume(products, onCancellation = {})
                } else {
                    continuation.resume(emptyList(), onCancellation = {})
                }
            }
        }

        // Перевіряємо, чи продукт знайдено
        val productDetails = productDetailsList.firstOrNull()
            ?: return PurchaseResult.Error("Product not found")

        // Запускаємо флоу покупки
        return suspendCancellableCoroutine { continuation ->
            purchaseCallback = { result ->
                if (continuation.isActive) continuation.resume(result)
                purchaseCallback = null
            }

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .build()
                    )
                )
                .build()

            billingClient.launchBillingFlow(activity, billingFlowParams)
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                val purchase = purchases?.firstOrNull()
                if (purchase != null) {
                    purchaseCallback?.invoke(
                        PurchaseResult.Success(
                            PurchasedProduct(
                                productId = purchase.products.first(),
                                purchaseToken = purchase.purchaseToken,
                                isAcknowledged = purchase.isAcknowledged
                            )
                        )
                    )
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                purchaseCallback?.invoke(PurchaseResult.Cancelled)
            }
            else -> {
                purchaseCallback?.invoke(PurchaseResult.Error("Purchase failed: ${result.debugMessage}"))
            }
        }
    }
}