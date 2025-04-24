package com.yeshuwahane.pupil.di

import com.yeshuwahane.pupil.data.local.MangaDao
import com.yeshuwahane.pupil.data.repositoryimpl.AuthRepositoryImpl
import com.yeshuwahane.pupil.data.repositoryimpl.MangaRepositoryImpl
import com.yeshuwahane.pupil.domain.repository.AuthRepository
import com.yeshuwahane.pupil.domain.repository.MangaRepository
import com.yeshuwahane.pupil.domain.usecase.AuthUseCase
import com.yeshuwahane.pupil.domain.usecase.MangaUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindMangaRepository(
        impl: MangaRepositoryImpl
    ): MangaRepository




}
