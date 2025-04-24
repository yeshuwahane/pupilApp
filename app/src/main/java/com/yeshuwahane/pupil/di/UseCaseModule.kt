package com.yeshuwahane.pupil.di

import com.yeshuwahane.pupil.data.local.MangaDao
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import com.yeshuwahane.pupil.domain.repository.MangaRepository
import com.yeshuwahane.pupil.domain.usecase.AuthUseCase
import com.yeshuwahane.pupil.domain.usecase.MangaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideMangaUseCase(
        repository: MangaRepository,
        dao: MangaDao
    ): MangaUseCase = MangaUseCase(repository, dao)

    @Provides
    @Singleton
    fun provideAuthUseCase(
        repository: AuthRepository
    ): AuthUseCase = AuthUseCase(repository)
}
