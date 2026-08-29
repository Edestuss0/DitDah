package com.ditdah.core.education.data

import com.ditdah.core.education.data.remote.EducationRemoteSource
import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.education.domain.repository.EducationRepository
import com.ditdah.core.exception.toAppException
import com.ditdah.core.settings.domain.entity.Language
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class EducationRepositoryImpl @Inject constructor(
    private val remote: EducationRemoteSource
) : EducationRepository {

    override suspend fun getLesson(language: Language): Result<Lesson> =
        runCatching { remote.generate(language) }.recoverCatching { throw it.toAppException() }
}