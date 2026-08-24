package com.ditdah.core.education.domain.repository

import com.ditdah.core.education.domain.entity.Lesson

internal interface EducationRepository {
    suspend fun getLessons(): List<Lesson>
}