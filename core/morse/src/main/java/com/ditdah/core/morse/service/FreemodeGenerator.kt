package com.ditdah.core.morse.service

import com.ditdah.core.morse.model.FreemodeDifficulty
import com.ditdah.core.morse.model.MorseQuestion
import com.ditdah.core.morse.model.MorseQuestionType
import com.ditdah.core.morse.model.textToMorseAlphabet
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

internal class FreemodeGenerator @Inject constructor() {

    fun generate(difficulty: FreemodeDifficulty): MorseQuestion {
        val length = when (difficulty) {
            FreemodeDifficulty.EASY -> Random.nextInt(1..3)
            FreemodeDifficulty.MEDIUM -> Random.nextInt(3..6)
            FreemodeDifficulty.HARD -> Random.nextInt(5..9)
        }

        return generateByLength(length)
    }

    private fun generateByLength(length: Int): MorseQuestion {
        val type = MorseQuestionType.entries.random()

        when (type) {
            MorseQuestionType.TEXT -> {
                var question = ""
                var answer = ""

                repeat(length) {
                    val letter = textToMorseAlphabet.entries.random()
                    question += letter.key
                    answer += "${letter.key}"
                }

                return MorseQuestion(
                    type = type,
                    answer = answer,
                    question = question
                )
            }
            MorseQuestionType.MORSE -> {
                var question = ""
                var answer = ""

                repeat(length) {
                    val letter = textToMorseAlphabet.entries.random()
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
                var question = ""
                var answer = ""

                repeat(length) {
                    val letter = textToMorseAlphabet.entries.random()
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