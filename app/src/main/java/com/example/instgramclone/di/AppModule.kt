package com.example.instgramclone.di

import com.example.instgramclone.datasource.InstgramCloneDataSource
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideInstgramCloneRepository(insdts:InstgramCloneDataSource) : InstgramCloneRepository {
        return InstgramCloneRepository(insdts)
    }

    @Provides
    @Singleton
    fun provideInstgramCloneDateSource() : InstgramCloneDataSource {
        return InstgramCloneDataSource()
    }
}