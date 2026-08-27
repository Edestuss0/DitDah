package com.ditdah.core.morse.domain.usecase

import com.ditdah.core.morse.domain.service.MorseEncoder
import javax.inject.Inject

class CodeMorseUseCase @Inject internal constructor(
    private val encoder: MorseEncoder
) {
    fun decode(text: String): String = encoder.decode(text)
    fun encode(text: String): String = encoder.encode(text)

    fun decodeLetter(text: String): String = encoder.decodeLetter(text)
    fun encodeLetter(text: String): String = encoder.encodeLetter(text)

}