package com.ditdah.core.user.di

import com.ditdah.core.user.data.repository.UserRepositoryImpl
import com.ditdah.core.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserRepositoryModule {
    @Binds @Singleton
    abstract fun bind(
        impl: UserRepositoryImpl
    ): UserRepository
}