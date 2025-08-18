package presentation.questions

import org.koin.dsl.module

val  questionModule = module {
    factory { QuestionViewModel(get()) }
}