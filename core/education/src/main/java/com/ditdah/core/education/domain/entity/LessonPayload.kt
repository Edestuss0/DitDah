package com.ditdah.core.education.domain.entity

sealed class LessonPayload {
    data class TextPayload(val question: String, val answer: String) : LessonPayload()
    data class TapPayload(val question: String, val answer: String) : LessonPayload()
    data class ListenPayload(val question: String, val answer: String) : LessonPayload()
    data class QuizPayload(val question: String, val options: List<String>, val correctIndex: Int) : LessonPayload()
}