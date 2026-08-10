package com.ditdah.core.morse.di

import com.ditdah.core.morse.data.repository.MorseRepositoryImpl
import com.ditdah.core.morse.repository.MorseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract internal class MorseModule {
    @Binds @Singleton
    abstract fun bindMorseRepository(
        impl: MorseRepositoryImpl
    ): MorseRepository
}