package com.ditdah.core.user.data.local

import javax.inject.Inject

internal class UserLocalSource @Inject constructor(
    private val datastore: UserDatastore
) {
    fun getAuthStatus() = datastore.get()
    suspend fun changeAuthStatus(status: Boolean) = if (status) datastore.authorize() else datastore.unauthorize()
}