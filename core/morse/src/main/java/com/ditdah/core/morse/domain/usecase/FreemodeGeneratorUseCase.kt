package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.entity.FreemodeDifficulty
import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.service.FreemodeGenerator
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import javax.inject.Inject

class FreemodeGeneratorUseCase @Inject internal constructor(
    private val generator: FreemodeGenerator,
    private val settings: GetSettingsUseCase
) {
    operator fun invoke(
        difficulty: FreemodeDifficulty = FreemodeDifficulty.MEDIUM,
        symbol: String? = null
    ): MorseQuestion.SimpleQuestion {
        val language = settings().value.language
        return generator.generate(difficulty, symbol, language)
    }
}