package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.entity.FreemodeDifficulty
import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.service.FreemodeGenerator
import javax.inject.Inject

class FreemodeGeneratorUseCase @Inject internal constructor(
    private val generator: FreemodeGenerator
) {
    operator fun invoke(difficulty: FreemodeDifficulty): MorseQuestion = generator.generate(difficulty)
}