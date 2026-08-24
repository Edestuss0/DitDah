package com.ditdah.core.user.domain.usecase

import com.ditdah.core.user.domain.repository.UserRepository
import javax.inject.Inject

class GetMeUseCase @Inject internal constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getMe()
}