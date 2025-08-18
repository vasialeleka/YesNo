package presentation.description

import org.koin.dsl.module

val descriptionModule = module {
    factory { DescriptionViewModel() }
}
