package com.ditdah.core.morse.usecase

import com.ditdah.core.morse.service.MorseEncoder
import javax.inject.Inject

class CodeMorseUseCase @Inject internal constructor(
    private val encoder: MorseEncoder
) {
    fun decode(text: String): String = encoder.decode(text)
    fun encode(text: String): String = encoder.encode(text)

}