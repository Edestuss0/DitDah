package com.ditdah.core.education.data

import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.education.domain.entity.LessonTask
import com.ditdah.core.education.domain.entity.LessonTaskType
import com.ditdah.core.exception.AppException
import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.entity.MorseQuestionType
import com.ditdah.core.morse.domain.entity.toMorseSymbols
import com.ditdah.core.settings.domain.entity.Language
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
internal data class LessonTaskDto(
    val id: Int,
    val lessonId: Int,
    val order: Int,
    val type: String,
    val payload: JsonElement
)

internal fun LessonTaskDto.toEntity(): LessonTask {
    val type = try {
        LessonTaskType.valueOf(type.uppercase())
    } catch (_: Exception) {
        LessonTaskType.TAP
    }
    val payloadJson = payload.jsonObject
    val payload = when (type) {
        LessonTaskType.TEXT -> {
            MorseQuestion.SimpleQuestion(
                question = payloadJson["question"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                answer = payloadJson["answer"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                type = MorseQuestionType.MORSE
            )
        }

        LessonTaskType.TAP -> {
            MorseQuestion.SimpleQuestion(
                question = payloadJson["question"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                answer = payloadJson["answer"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                type = MorseQuestionType.TEXT
            )
        }

        LessonTaskType.LISTEN -> {
            MorseQuestion.SimpleQuestion(
                question = payloadJson["question"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                answer = payloadJson["answer"]?.jsonPrimitive?.content?.toMorseSymbols()
                    ?: throw AppException.Serialization,
                type = MorseQuestionType.AUDIO
            )
        }

        LessonTaskType.QUIZ -> {
            val options = payloadJson["options"]?.let {
                Json.decodeFromJsonElement<List<String>>(it)
            }?.map { it.toMorseSymbols() } ?: throw AppException.Serialization

            MorseQuestion.QuizQuestion(
                question = payloadJson["question"]?.jsonPrimitive?.content
                    ?: throw AppException.Serialization,
                options = options,
                correctIndex = payloadJson["correctIndex"]?.jsonPrimitive?.intOrNull
                    ?: throw AppException.Serialization
            )
        }
    }

    return LessonTask(
        id = id,
        lessonId = lessonId,
        order = order,
        type = type,
        payload = payload
    )
}

@Serializable
internal data class LessonDto(
    val id: Int,
    val order: Int,
    val title: String,
    val description: String,
    val xpReward: Int,
    val language: String,
    val tasks: List<LessonTaskDto>
)

internal fun LessonDto.toEntity(): Lesson = Lesson(
    id = id,
    order = order,
    title = title,
    description = description,
    xpReward = xpReward,
    language = runCatching { Language.valueOf(language.uppercase()) }.getOrElse { throw AppException.Serialization },
    tasks = tasks.map { it.toEntity() }
)