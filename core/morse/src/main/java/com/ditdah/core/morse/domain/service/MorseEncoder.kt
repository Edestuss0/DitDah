package com.ditdah.core.morse.domain.service

import com.ditdah.core.morse.domain.entity.morseToTextAlphabet
import com.ditdah.core.morse.domain.entity.morseToTextAlphabetRu
import com.ditdah.core.morse.domain.entity.textToMorseAlphabet
import com.ditdah.core.morse.domain.entity.textToMorseAlphabetRu
import com.ditdah.core.settings.domain.entity.Language
import javax.inject.Inject

internal class MorseEncoder @Inject constructor() {
    fun encodeLetter(text: String, language: Language): String = when (language) {
        Language.EN -> textToMorseAlphabet[text] ?: ""
        Language.RU -> textToMorseAlphabetRu[text] ?: ""
    }

    fun decodeLetter(morse: String, language: Language): String = when (language) {
        Language.EN -> morseToTextAlphabet[morse] ?: ""
        Language.RU -> morseToTextAlphabetRu[morse] ?: ""
    }
    fun encode(text: String, language: Language): String = text.trim().map { encodeLetter(it.toString(), language) }.joinToString(" ")
    fun decode(text: String, language: Language): String = text.trim().split("\\s+".toRegex()).mapNotNull { when (language) {
        Language.EN -> morseToTextAlphabet[it]
        Language.RU -> morseToTextAlphabetRu[it]
    }}.joinToString("")
}