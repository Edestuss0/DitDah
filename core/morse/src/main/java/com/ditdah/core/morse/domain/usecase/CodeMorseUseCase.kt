package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.service.MorseEncoder
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import javax.inject.Inject

class CodeMorseUseCase @Inject internal constructor(
    private val encoder: MorseEncoder,
    private val settings: GetSettingsUseCase
) {
    fun decode(text: String): String {
        val language = settings().value.language
        return encoder.decode(text, language)
    }
    fun encode(text: String): String {
        val language = settings().value.language
        return encoder.encode(text, language)
    }

    fun decodeLetter(text: String): String {
        val language = settings().value.language
        return encoder.decodeLetter(text, language)
    }
    fun encodeLetter(text: String): String {
        val language = settings().value.language
        return encoder.encodeLetter(text, language)
    }

}