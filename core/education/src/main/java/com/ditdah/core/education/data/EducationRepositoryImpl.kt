package com.ditdah.core.education.data

import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.education.domain.repository.EducationRepository
import javax.inject.Singleton

@Singleton
internal class EducationRepositoryImpl : EducationRepository {

    override suspend fun getLessons(): List<Lesson> {

    }
}