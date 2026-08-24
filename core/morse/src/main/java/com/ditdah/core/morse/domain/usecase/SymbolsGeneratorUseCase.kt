package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.service.SymbolsGenerator
import javax.inject.Inject

class SymbolsGeneratorUseCase @Inject internal constructor(
    private val generator: SymbolsGenerator
){
    operator fun invoke(symbol: String) = generator.generate(symbol)
}