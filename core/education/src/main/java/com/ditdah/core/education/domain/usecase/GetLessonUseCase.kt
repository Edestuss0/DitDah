package com.ditdah.core.education.domain.usecase

import com.ditdah.core.education.domain.repository.EducationRepository
import com.ditdah.core.settings.domain.entity.Language
import javax.inject.Inject

class GetLessonUseCase @Inject internal constructor(
    private val repository: EducationRepository
) {
    suspend operator fun invoke(language: Language) = repository.getLesson(language)
}