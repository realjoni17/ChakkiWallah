package com.android.chakkiwallah.di

import com.android.chakkiwallah.data.repository.FirebaseRepositoryImpl
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaeModule{
    @Singleton
    @Provides
    fun provideFirebaseRealtimeDatabase(
    ): FirebaseDatabase {
        return FirebaseDatabase.getInstance("https://care-46cc1-default-rtdb.firebaseio.com/")
    }

    @Provides
    @Singleton
    fun providesFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseRepository(
        firestore: FirebaseFirestore

    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            fireStore = firestore
        )
    }
}