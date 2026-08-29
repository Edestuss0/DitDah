package com.ditdah.core.education.domain.entity

import com.ditdah.core.morse.domain.entity.MorseQuestion

data class LessonTask(
    val id: Int,
    val lessonId: Int,
    val order: Int,
    val type: LessonTaskType,
    val payload: MorseQuestion
)

enum class LessonTaskType {
    TEXT, TAP, LISTEN, QUIZ
}
