package presentation.main

import org.koin.dsl.module

val mainCardsModule = module {
    factory { MainCardsViewModel(get()) }
}