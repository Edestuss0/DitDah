//package com.ditdah.core.education.data
//
//import com.ditdah.core.education.domain.entity.LessonPayload
//import com.ditdah.core.education.domain.entity.LessonTask
//import com.ditdah.core.education.domain.entity.LessonTaskType
//import com.ditdah.core.morse.domain.entity.toMorseSymbols
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.jsonObject
//import kotlinx.serialization.json.jsonPrimitive
//
//@Serializable
//internal data class LessonTaskDto (
//    val id: Int,
//    val lessonId: Int,
//    val order: Int,
//    val type: String,
//    val payload: String
//)
//
//internal fun LessonTaskDto.toEntity(): LessonTask {
//    val type = try { LessonTaskType.valueOf(type.uppercase()) } catch (_: Exception) { LessonTaskType.TAP }
//    val payloadJson = Json.parseToJsonElement(payload).jsonObject
//    val payload = when (type) {
//        LessonTaskType.TEXT -> {
//            LessonPayload.TextPayload(question = (payloadJson["morse"]?.jsonPrimitive?.content ?: "").toMorseSymbols(), answer = (payloadJson["morse"]?.jsonPrimitive?.content ?: "").toMorseSymbols() )
//        }
//        LessonTaskType.TAP -> {
//            val (question, answer) = payload.split("|")
//            LessonPayload.TapPayload(question, answer)
//        }
//        LessonTaskType.LISTEN -> {
//            val (question, answer) = payload.split("|")
//            LessonPayload.ListenPayload(question, answer)
//        }
//        LessonTaskType.QUIZ -> {
//            val (question, options, correctIndex) = payload.split("|")
//            LessonPayload.QuizPayload(question, options.split(","), correctIndex.toInt())
//        }
//        }
//    }
//}