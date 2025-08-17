package base

import data.FirestoreGameRepository
import domain.GameRepository
import org.koin.dsl.module

val baseModule = module {
    single<GameRepository> { FirestoreGameRepository() }
}