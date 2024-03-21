package com.example.instgramclone.di

import com.example.instgramclone.datasource.InstgramCloneDataSource
import com.example.instgramclone.repo.InstgramCloneRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideInstgramCloneDateSource(collectionUsers:CollectionReference) : InstgramCloneDataSource {
        return InstgramCloneDataSource(collectionUsers)
    }

    @Provides
    @Singleton
    fun provideCollectionReference() : CollectionReference {
        return Firebase.firestore.collection("Users")
    }
}