package com.unsmoke.app.di

import com.unsmoke.app.core.data.repository.*
import com.unsmoke.app.core.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuitAttemptRepository(impl: QuitAttemptRepositoryImpl): QuitAttemptRepository

    @Binds
    @Singleton
    abstract fun bindCravingRepository(impl: CravingRepositoryImpl): CravingRepository

    @Binds
    @Singleton
    abstract fun bindNRTRepository(impl: NRTRepositoryImpl): NRTRepository

    @Binds
    @Singleton
    abstract fun bindCheckInRepository(impl: CheckInRepositoryImpl): CheckInRepository
}
