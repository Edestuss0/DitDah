package com.ditdah.core.education.domain.entity

import com.ditdah.core.settings.domain.entity.Language

data class Lesson(
    val id: Int,
    val order: Int,
    val title: String,
    val description: String,
    val tasks: List<LessonTask>,
    val language: Language,
    val xpReward: Int
)
