package com.ditdah.features.education.home.viewmodel

import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.settings.domain.entity.Language

data class EducationHomeState(
    val lesson: Lesson? = null,
    val isLoading: Boolean = false,
    val language: Language? = null
)

sealed class EducationEvent {
    data object Refresh : EducationEvent()
}

sealed class EducationEffect {
    data class Error(val message: String) : EducationEffect()
}