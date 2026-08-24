package com.ditdah.core.morse.domain.service

import com.ditdah.core.morse.domain.entity.morseToTextAlphabet
import com.ditdah.core.morse.domain.entity.textToMorseAlphabet
import javax.inject.Inject

internal class MorseEncoder @Inject constructor() {
    fun encode(text: String): String = textToMorseAlphabet[text] ?: ""

    fun decode(morse: String): String = morseToTextAlphabet[morse] ?: ""
}