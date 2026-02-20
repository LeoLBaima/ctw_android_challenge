package com.leob.news.features.home.di

import com.leob.news.features.home.data.repository.HomeRepositoryImpl
import com.leob.news.features.home.data.service.HomeService
import com.leob.news.features.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    @Singleton
    fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    companion object {
        @Provides
        @Singleton
        fun provideHomeService(retrofit: Retrofit): HomeService =
            retrofit.create(HomeService::class.java)
    }
}