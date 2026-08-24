package com.ditdah.core.morse.domain.service

import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.entity.MorseQuestionType
import com.ditdah.core.morse.domain.entity.textToMorseAlphabet
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

internal class SymbolsGenerator @Inject constructor() {

    fun generate(symbol: String): MorseQuestion {
        val length = Random.nextInt(1..5)
        return generatePracticeToSymbolByLength(symbol, length)
    }

    private fun generatePracticeToSymbolByLength(symbol: String, length: Int): MorseQuestion {
        val type = MorseQuestionType.entries.random()

        when (type) {
            MorseQuestionType.TEXT -> {
                val letter = textToMorseAlphabet.entries.find { it.key == symbol }
                if (letter == null) {
                    throw IllegalArgumentException("Информация по данному символу не найдена")
                }
                var question = ""
                var answer = ""

                repeat(length) {
                    question += letter.key
                    answer += letter.key
                }

                return MorseQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }
            MorseQuestionType.MORSE -> {
                val letter = textToMorseAlphabet.entries.find { it.key == symbol }
                if (letter == null) {
                    throw IllegalArgumentException("Информация по данному символу не найдена")
                }
                var question = ""
                var answer = ""

                repeat(length) {
                    question += "${letter.value} "
                    answer += letter.key
                }
                return MorseQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }

            MorseQuestionType.AUDIO -> {
                val letter = textToMorseAlphabet.entries.find { it.key == symbol }
                if (letter == null) {
                    throw IllegalArgumentException("Информация по данному символу не найдена")
                }
                var question = ""
                var answer = ""

                repeat(length) {
                    question += "${letter.value} "
                    answer += letter.key
                }
                return MorseQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }
        }
    }
}