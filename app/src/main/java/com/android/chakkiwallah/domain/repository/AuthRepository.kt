package com.android.chakkiwallah.domain.repository

import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.AuthUser
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    suspend fun addUserToFireStore()

        fun signInWithCredential(
            credentials: AuthCredential,
            user: AuthUser
        ): Flow<Resource<AuthResult>>

    }
