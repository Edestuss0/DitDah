package com.ditdah.core.db.features.user

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE isMe = 1 LIMIT 1")
    fun observeMe(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)


    @Query("DELETE FROM users WHERE id = :id")
    suspend fun invalidate(id: Int)

    @Query("DELETE FROM users WHERE isMe = 1")
    suspend fun invalidateMe()

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("DELETE FROM users WHERE cachedAt < :threshold")
    suspend fun clearExpired(threshold: Long)
}
