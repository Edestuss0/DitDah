package com.ditdah.core.db.migrations

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL


internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN xp INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN level INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN lessonDoneEn INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN lessonDoneRu INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN dayStreak INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
        connection.execSQL(
            """
            ALTER TABLE users
            ADD COLUMN answerStreak INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
    }
}