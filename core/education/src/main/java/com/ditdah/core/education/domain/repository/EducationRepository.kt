package com.ditdah.core.education.domain.repository

import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.settings.domain.entity.Language

internal interface EducationRepository {
    suspend fun getLesson(language: Language): Result<Lesson>
}