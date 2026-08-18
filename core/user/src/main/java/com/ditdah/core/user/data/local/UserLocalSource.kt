package com.ditdah.core.user.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.ditdah.core.db.features.user.UserDao
import com.ditdah.core.db.features.user.UserEntity
import com.ditdah.core.di.ApplicationScope
import com.ditdah.core.di.IoDispatcher
import com.ditdah.core.user.data.UserDto
import com.ditdah.core.user.data.toDto
import com.ditdah.core.user.data.toEntity
import com.ditdah.core.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class UserLocalSource @Inject constructor(
    private val datastore: UserDatastore,
    private val dao: UserDao,
) {
    private suspend fun clearExpired(duration: Long) {
        dao.clearExpired(System.currentTimeMillis() - duration)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun observeMe(): Flow<User?> {
        return dao.observeMe().map { (it?.json)?.let { string -> Json.decodeFromString<UserDto>(string) }
            ?.toEntity() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserById(id: Int, duration: Long): User? {
        clearExpired(duration)
        return dao.getUserById(id)?.let { Json.decodeFromString<UserDto>(it.json) }?.toEntity()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserByName(name: String, duration: Long): User? {
        clearExpired(duration)
        return dao.getUserByUsername(name)?.let { Json.decodeFromString<UserDto>(it.json) }?.toEntity()
    }

    suspend fun insert(isMe: Boolean, user: User) {
        dao.insert(UserEntity(
            id = user.id,
            isMe = isMe,
            json = Json.encodeToString(user.toDto()),
            cachedAt = System.currentTimeMillis(),
            username = user.username
        ))
    }

    suspend fun invalidateMe() = dao.invalidateMe()

    suspend fun invalidate(id: Int) = dao.invalidate(id)

    suspend fun clearAll() = dao.clearAll()

    fun getAuthStatus() = datastore.get()
    suspend fun changeAuthStatus(status: Boolean) = if (status) datastore.authorize() else datastore.unauthorize()
}