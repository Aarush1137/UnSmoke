package com.unsmoke.app.di

import android.content.Context
import androidx.room.Room
import com.unsmoke.app.core.data.database.UnSmokeDatabase
import com.unsmoke.app.core.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUnSmokeDatabase(@ApplicationContext context: Context): UnSmokeDatabase {
        return Room.databaseBuilder(
            context,
            UnSmokeDatabase::class.java,
            "unsmoke_db")
            .addMigrations(UnSmokeDatabase.MIGRATION_1_2, UnSmokeDatabase.MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideQuitAttemptDao(db: UnSmokeDatabase): QuitAttemptDao = db.quitAttemptDao()

    @Provides
    fun provideCravingDao(db: UnSmokeDatabase): CravingDao = db.cravingDao()

    @Provides
    fun provideNRTDao(db: UnSmokeDatabase): NRTDao = db.nrtDao()

    @Provides
    fun provideCheckInDao(db: UnSmokeDatabase): CheckInDao = db.checkInDao()

    @Provides
    fun provideUserProfileDao(db: UnSmokeDatabase): UserProfileDao = db.userProfileDao()

    @Provides
    fun provideRewardGoalDao(database: UnSmokeDatabase): RewardGoalDao {
        return database.rewardGoalDao()
    }
}