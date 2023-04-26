package com.android.chakkiwallah.di

import com.android.chakkiwallah.data.repository.FirebaseRepositoryImpl
import com.android.chakkiwallah.data.repository.AuthRepositoryImpl
import com.android.chakkiwallah.domain.repository.FirebaseRepository
import com.android.chakkiwallah.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
        firestore: FirebaseFirestore,
         firebasAuth: FirebaseAuth
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            fireStore = firestore,
            firebaseAuth = firebasAuth
        )
    }
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore):AuthRepository
    {
        return AuthRepositoryImpl(
            fireStore = fireStore,
            firebaseAuth =  auth
        )
    }
}