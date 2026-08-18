package com.ditdah.core.db

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.ditdah.core.db.features.user.UserDao
import com.ditdah.core.db.features.user.UserEntity


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class MainDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}