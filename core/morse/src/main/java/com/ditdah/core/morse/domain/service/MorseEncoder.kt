package com.ditdah.core.morse.domain.service

import com.ditdah.core.morse.domain.entity.morseToTextAlphabet
import com.ditdah.core.morse.domain.entity.textToMorseAlphabet
import javax.inject.Inject

internal class MorseEncoder @Inject constructor() {
    fun encodeLetter(text: String): String = textToMorseAlphabet[text] ?: ""

    fun decodeLetter(morse: String): String = morseToTextAlphabet[morse] ?: ""
    fun encode(text: String): String = text.trim().map { encodeLetter(it.toString()) }.joinToString(" ")
    fun decode(text: String): String = text.trim().split("\\s+".toRegex()).mapNotNull { morseToTextAlphabet[it] }.joinToString("")
}