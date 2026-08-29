package com.ditdah.core.education.di

import com.ditdah.core.education.data.EducationRepositoryImpl
import com.ditdah.core.education.domain.repository.EducationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class EducationModule {
    @Singleton
    @Binds
    abstract fun bindEducationRepository(
        impl: EducationRepositoryImpl
    ): EducationRepository
}