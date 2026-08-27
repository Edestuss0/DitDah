package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.entity.morseLetters
import com.ditdah.core.morse.domain.entity.morseLettersRu
import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import javax.inject.Inject

class GetAlphabetUseCase @Inject constructor(
    private val settings: GetSettingsUseCase
) {
    operator fun invoke(): Map<String, String> {
        val lang = settings().value.language
        return when (lang) {
            Language.EN -> morseLetters
            Language.RU -> morseLettersRu
        }
    }
}