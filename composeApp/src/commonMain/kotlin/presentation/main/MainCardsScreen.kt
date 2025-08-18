package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import base.BaseScreen
import base.EmptyInitData
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.Navigator
import presentation.description.DescriptionScreen
import presentation.description.model.DescriptionInitData
import presentation.main.сomponents.GamePackCardComponent
import presentation.questions.QuestionScreen
import presentation.questions.model.QuestionInitData

class MainCardsScreen : BaseScreen<MainCardsViewModel, MainCardsState, EmptyInitData>(){

    @Composable
    override fun provideVm() = koinScreenModel<MainCardsViewModel>()

    @Composable
    override fun Content(
        state: MainCardsState,
        viewModel: MainCardsViewModel,
        navigator: Navigator
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .windowInsetsPadding(WindowInsets(0))
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "What will we play today?",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            itemsIndexed(state.packs) { index, game ->

                GamePackCardComponent(
                    game = game,
                    isLast = state.packs.size - 1 == index,
                    isPaid = false
                    /*state.payedProducts.contains(Product.MonthSubs) || state.payedProducts.contains(
                        Product.getById(game.productId)
                    )*/,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-48 * index).dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .zIndex(state.packs.size + index.toFloat()),
                    onClick = {
                       /* navigator.push(
                            QuestionScreen(
                                QuestionInitData(
                                    id = game.id,
                                    colorHex = game.colorHex,
                                    title = game.getTitle()
                                )
                            )
                        )*/

                        navigator.push(
                            DescriptionScreen(
                                DescriptionInitData(
                                    id = game.id,
                                    colorHex = game.colorHex,
                                    title = game.getTitle(),
                                    description = game.getDescription()
                                )
                            )
                        )
                        /*  Log.d("Networkk", "Gme details ${game}")
                          if (state.payedProducts.contains(Product.MonthSubs) || state.payedProducts.contains(
                                  Product.getById(game.productId)
                              ) || game.ifYouHadToPay()
                          ) {
                              onNavigationAction.invoke(
                                  NavigateActions.NavigateToGame(
                                      game.id,
                                      game.colorHex,
                                      game.getTitle()
                                  )
                              )
                          } else {
                              onNavigationAction.invoke(
                                  NavigateActions.NavigateToPayment(
                                      game.productId,
                                      game.colorHex,
                                      game.getTitle(),
                                      game.productId
                                  )
                              )
                          }*/
                    }
                )
            }
        }
    }
}