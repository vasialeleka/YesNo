package presentation.description

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import base.BaseScreen
import base.components.NextButton
import base.components.TransparentButton
import base.fromHex
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.parameter.parametersOf
import presentation.description.model.DescriptionInitData

class DescriptionScreen(
    private val initData: DescriptionInitData
) : BaseScreen<DescriptionViewModel, DescriptionState, DescriptionInitData>() {

    override fun provideInitData() = initData

    @Composable
    override fun provideVm(): DescriptionViewModel = koinScreenModel(
        parameters = { parametersOf(initData) }
    )

    @Composable
    override fun Content(
        state: DescriptionState,
        viewModel: DescriptionViewModel,
        navigator: Navigator
    ) {
       /* val activity = LocalContext.current as? Activity
        val scope = rememberCoroutineScope()

        val productDetails = remember { mutableStateOf<List<ProductDetails>>(emptyList()) }
        val productDetails2 = remember { mutableStateOf<List<ProductDetails>>(emptyList()) }

       */

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fromHex(state.colorHex)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = initData.title,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(48.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(vertical = 22.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Wow, you want to buy the card game series: “${initData.title}” ?",
                        style = TextStyle(fontSize = 22.sp, color = Color.Black),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Medium
                    )

                    state.cardDescription.forEach { desc ->
                        Text(
                            text = "• $desc",
                            style = TextStyle(fontSize = 22.sp, color = Color.Black),
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp, horizontal = 16.dp)
            ) {
              /*  val price = productDetails2.value.firstOrNull()
                    ?.oneTimePurchaseOfferDetails?.formattedPrice
                val subPrice = productDetails.value.firstOrNull()
                    ?.subscriptionOfferDetails?.firstOrNull()
                    ?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
                val billingPeriod = productDetails.value.firstOrNull()
                    ?.subscriptionOfferDetails?.firstOrNull()
                    ?.pricingPhases?.pricingPhaseList?.firstOrNull()?.billingPeriod
*/
                TransparentButton(
                    text =  "Buy",
                    onClick = {
                       /* scope.launch {
                            val result = viewModel.purchaseProduct(
                                activity!!,
                                productDetails2.value.firstOrNull()!!
                            )
                            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                            }
                        }*/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                NextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    isArrowVisible = false,
                    title =/* subPrice?.let {
                        "Subscribe for $subPrice/${parseBillingPeriod(billingPeriod)}"
                    } ?: */"Or subscribe"
                ) {
                   /* scope.launch {
                        val result = viewModel.purchaseProduct(
                            activity!!,
                            productDetails.value.firstOrNull()!!
                        )
                    }*/
                }
            }
        }
    }
    fun parseBillingPeriod(billingPeriod: String?): String {
        if (billingPeriod.isNullOrEmpty()) return "Unknown period"

        return when {
            billingPeriod.contains("P1W") -> "week"
            billingPeriod.contains("P1M") -> "month"
            billingPeriod.contains("P3M") -> "3 months"
            billingPeriod.contains("P6M") -> "6 months"
            billingPeriod.contains("P1Y") -> "year"
            else -> ""
        }
    }
}