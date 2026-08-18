package com.ditdah.core.db.features.user

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val json: String,
    val cachedAt: Long = System.currentTimeMillis(),
    val isMe: Boolean,
    val username: String
)
