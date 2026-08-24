package com.ditdah.core.user.domain.entity

data class RegisterInput(
    val username: String,
    val password: String
)

data class LoginInput(
    val username: String,
    val password: String
)