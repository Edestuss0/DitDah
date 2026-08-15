package com.ditdah.core.user.usecase

import com.ditdah.core.user.repository.UserRepository
import javax.inject.Inject

class GetMeUseCase @Inject internal constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getMe()
}