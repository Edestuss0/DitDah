package com.ditdah.core.db

import android.content.Context
import androidx.room3.Room
import com.ditdah.core.db.features.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DBModule {

    @Provides @Singleton
    fun provideMainDB(
        @ApplicationContext context: Context
    ): MainDB = Room.databaseBuilder(
        context.applicationContext,
        MainDB::class.java,
        "main.db"
    ).fallbackToDestructiveMigration(true).build()

    @Provides
    fun provideUserDao(mainDB: MainDB): UserDao {
        return mainDB.userDao()
    }
}