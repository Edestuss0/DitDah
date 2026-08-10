package com.ditdah.core.morse.service

import com.ditdah.core.morse.model.morseToTextAlphabet
import com.ditdah.core.morse.model.textToMorseAlphabet
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class MorseEncoder @Inject constructor() {
    fun encode(text: String): String = textToMorseAlphabet[text] ?: ""

    fun decode(morse: String): String = morseToTextAlphabet[morse] ?: ""
}