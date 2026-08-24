package com.ditdah.core.education.domain.entity

data class LessonTask(
    val id: Int,
    val lessonId: Int,
    val order: Int,
    val type: LessonTaskType,
    val payload: LessonPayload
)

enum class LessonTaskType {
    TEXT, TAP, LISTEN, QUIZ
}
