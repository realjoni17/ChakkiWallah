package com.android.chakkiwallah.domain.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.android.chakkiwallah.domain.model.Cart
import com.android.chakkiwallah.domain.model.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun firebaseSignIn(user: AuthUser): Flow<Resource<String>>

    fun firebaseSignUp(user: AuthUser): Flow<Resource<String>>

    suspend fun getAllProducts() :Resource<List<Product>>



    fun currentUserExist(): Flow<Boolean>

    fun signOut()

    fun currentUser(): FirebaseUser?

}
