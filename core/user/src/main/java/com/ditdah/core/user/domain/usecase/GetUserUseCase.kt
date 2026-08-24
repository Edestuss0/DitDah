package com.ditdah.core.user.domain.usecase

import com.ditdah.core.user.domain.entity.User
import com.ditdah.core.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject internal constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int): Result<User> = repository.getUserById(id)
}