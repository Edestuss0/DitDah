package com.ditdah.core.morse.domain.service

import com.ditdah.core.morse.domain.entity.FreemodeDifficulty
import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.entity.MorseQuestionType
import com.ditdah.core.morse.domain.entity.textToMorseAlphabet
import com.ditdah.core.morse.domain.entity.textToMorseAlphabetRu
import com.ditdah.core.settings.domain.entity.Language
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

internal class FreemodeGenerator @Inject constructor() {

    fun generate(
        difficulty: FreemodeDifficulty,
        symbol: String? = null,
        language: Language
    ): MorseQuestion.SimpleQuestion {
        val length = when {
            symbol == null -> Random.nextInt(2..5)
            difficulty == FreemodeDifficulty.EASY -> Random.nextInt(1..3)
            difficulty == FreemodeDifficulty.MEDIUM -> Random.nextInt(3..6)
            difficulty == FreemodeDifficulty.HARD -> Random.nextInt(5..9)
            else -> 3
        }

        return generateByLength(length, symbol, language)
    }

    private fun generateByLength(
        length: Int,
        symbol: String?,
        language: Language
    ): MorseQuestion.SimpleQuestion {
        val type = MorseQuestionType.entries.random()

        when (type) {
            MorseQuestionType.TEXT -> {
                var question = ""
                var answer = ""

                repeat(length) {
                    val letter = when (language) {
                        Language.EN -> if (symbol == null) textToMorseAlphabet.entries.random() else textToMorseAlphabet.entries.find { it.key == symbol }
                        Language.RU -> if (symbol == null) textToMorseAlphabetRu.entries.random() else textToMorseAlphabetRu.entries.find { it.key == symbol }
                    }
                    if (letter == null) { throw IllegalArgumentException("Информация по данному символу не найдена") }
                    question += letter.key
                    answer += letter.key
                }

                return MorseQuestion.SimpleQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }
            MorseQuestionType.MORSE, MorseQuestionType.AUDIO -> {
                var question = ""
                var answer = ""

                repeat(length) {
                    val letter = when (language) {
                        Language.EN -> if (symbol == null) textToMorseAlphabet.entries.random() else textToMorseAlphabet.entries.find { it.key == symbol }
                        Language.RU -> if (symbol == null) textToMorseAlphabetRu.entries.random() else textToMorseAlphabetRu.entries.find { it.key == symbol }
                    }
                    if (letter == null) { throw IllegalArgumentException("Информация по данному символу не найдена") }
                    question += "${letter.value} "
                    answer += letter.key
                }
                return MorseQuestion.SimpleQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }
        }
    }
}