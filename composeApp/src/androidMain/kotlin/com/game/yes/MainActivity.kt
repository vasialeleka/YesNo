package com.game.yes

import App
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import base.baseModule
import base.initKoin
import billing.BillingRepository
import billing.billingModule
import com.google.firebase.FirebaseApp
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import presentation.description.descriptionModule
import presentation.main.mainCardsModule
import presentation.questions.questionModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        initKoin(billingModule(this@MainActivity), baseModule, mainCardsModule, descriptionModule, questionModule)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}