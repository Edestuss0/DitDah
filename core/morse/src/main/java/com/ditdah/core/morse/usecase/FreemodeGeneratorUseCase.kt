package com.ditdah.core.morse.usecase

import com.ditdah.core.morse.model.FreemodeDifficulty
import com.ditdah.core.morse.model.MorseQuestion
import com.ditdah.core.morse.service.FreemodeGenerator
import javax.inject.Inject

class FreemodeGeneratorUseCase @Inject internal constructor(
    private val generator: FreemodeGenerator
) {
    operator fun invoke(difficulty: FreemodeDifficulty): MorseQuestion = generator.generate(difficulty)
}