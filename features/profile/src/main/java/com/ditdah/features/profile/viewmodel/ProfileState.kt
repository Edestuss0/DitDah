package com.ditdah.features.profile.viewmodel

import com.ditdah.core.user.domain.entity.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
)
